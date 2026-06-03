package com.ldb.iadoc.Logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@Aspect
@Component
public class ApiActionLogAspect {
    @Autowired
    private ApiActionLogService logService;

    // Log every controller action centrally, but skip statistic/log endpoints to avoid recursion/noise.
    @Around("within(com.ldb.iadoc.Contrller..*)")
    public Object aroundControllers(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        MappingInfo mapping = MappingInfo.from(method);
        if (mapping == null) {
            return pjp.proceed();
        }

        // Skip read-log endpoints (they already deal with login_log) and login endpoint (already logs via saveLoginLog()).
        String path = mapping.path == null ? "" : mapping.path;
        if (path.startsWith("/log/") || "/Auth/login".equals(path)) {
            return pjp.proceed();
        }

        Object result = pjp.proceed();

        String userKey = ArgExtract.firstNonBlank(
                ArgExtract.findStringLike(pjp.getArgs(), "getUserName", "userName"),
                ArgExtract.findStringLike(pjp.getArgs(), "getUserId", "userId"),
                ArgExtract.findStringLike(pjp.getArgs(), "getMarkerId", "markerId"),
                ArgExtract.findStringLike(pjp.getArgs(), "getUpdateId", "updateId"),
                ArgExtract.findStringLike(pjp.getArgs(), "getDeleteId", "deleteId"),
                ArgExtract.findStringLike(pjp.getArgs(), "getApproveId", "approveId"),
                ArgExtract.findStringLike(pjp.getArgs(), "getReadBy", "readBy"),
                ArgExtract.findStringLike(pjp.getArgs(), "getRejectBy", "rejectBy"),
                ArgExtract.findStringLike(pjp.getArgs(), "getShareUserById", "shareUserById")
        );

        String docNo = ArgExtract.firstNonBlank(
                ArgExtract.findStringLike(pjp.getArgs(), "getDocNo", "docNo"),
                ArgExtract.findStringLike(pjp.getArgs(), "getDocno", "docno")
        );

        String subjectName = ArgExtract.firstNonBlank(
                ArgExtract.findStringLike(pjp.getArgs(), "getSubjectName", "subjectName"),
                ArgExtract.findStringLike(pjp.getArgs(), "getRelated_Name", "related_Name"),
                ArgExtract.findStringLike(pjp.getArgs(), "getRelatedName", "relatedName")
        );

        String type = deriveType(path);
        String nameDesc = mapping.httpMethod + " " + path;

        logService.logAction(userKey, type, docNo, subjectName, nameDesc);
        return result;
    }

    private static String deriveType(String path) {
        if (path == null) {
            return "api";
        }
        String p = path.toLowerCase();
        if (p.startsWith("/document/") || p.startsWith("/share/") || p.startsWith("/audit/")) {
            return "doc";
        }
        if (p.startsWith("/auth/") || p.startsWith("/user/")) {
            return "login";
        }
        return "api";
    }

    private static final class MappingInfo {
        final String httpMethod;
        final String path;

        private MappingInfo(String httpMethod, String path) {
            this.httpMethod = httpMethod;
            this.path = path;
        }

        static MappingInfo from(Method m) {
            if (m.isAnnotationPresent(GetMapping.class)) {
                return new MappingInfo("GET", firstPath(m.getAnnotation(GetMapping.class).value(), m.getAnnotation(GetMapping.class).path()));
            }
            if (m.isAnnotationPresent(PostMapping.class)) {
                return new MappingInfo("POST", firstPath(m.getAnnotation(PostMapping.class).value(), m.getAnnotation(PostMapping.class).path()));
            }
            if (m.isAnnotationPresent(PutMapping.class)) {
                return new MappingInfo("PUT", firstPath(m.getAnnotation(PutMapping.class).value(), m.getAnnotation(PutMapping.class).path()));
            }
            if (m.isAnnotationPresent(DeleteMapping.class)) {
                return new MappingInfo("DELETE", firstPath(m.getAnnotation(DeleteMapping.class).value(), m.getAnnotation(DeleteMapping.class).path()));
            }
            if (m.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping rm = m.getAnnotation(RequestMapping.class);
                String method = (rm.method().length > 0) ? rm.method()[0].name() : "REQUEST";
                return new MappingInfo(method, firstPath(rm.value(), rm.path()));
            }
            return null;
        }

        private static String firstPath(String[] v1, String[] v2) {
            if (v1 != null && v1.length > 0 && v1[0] != null && !v1[0].isEmpty()) {
                return v1[0];
            }
            if (v2 != null && v2.length > 0 && v2[0] != null && !v2[0].isEmpty()) {
                return v2[0];
            }
            return null;
        }
    }

    private static final class ArgExtract {
        static String firstNonBlank(String... vals) {
            if (vals == null) return null;
            for (String v : vals) {
                if (v != null && !v.trim().isEmpty()) return v.trim();
            }
            return null;
        }

        static String findStringLike(Object[] args, String getterName, String fieldName) {
            if (args == null) return null;
            for (Object a : args) {
                if (a == null) continue;
                // Try getter method first.
                try {
                    Method gm = a.getClass().getMethod(getterName);
                    Object v = gm.invoke(a);
                    if (v instanceof String) return (String) v;
                } catch (Exception ignore) {
                }
                // Try direct field access (some request objects might not use Lombok getters).
                try {
                    java.lang.reflect.Field f = a.getClass().getDeclaredField(fieldName);
                    f.setAccessible(true);
                    Object v = f.get(a);
                    if (v instanceof String) return (String) v;
                } catch (Exception ignore) {
                }
            }
            return null;
        }
    }
}

