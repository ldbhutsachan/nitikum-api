package com.ldb.iadoc.Contrller;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Branch.BranchRes;
import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranchRes;
import com.ldb.iadoc.Model.Department.DeptReq;
import com.ldb.iadoc.Model.Department.DeptRes;
import com.ldb.iadoc.Model.Login.*;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwdRes;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionRes;
import com.ldb.iadoc.Model.Section.SectionReq;
import com.ldb.iadoc.Model.Section.SectionRes;
import com.ldb.iadoc.Model.UserType.UserTypeRes;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserReq;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserRes;
import com.ldb.iadoc.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Auth/login endpoints plus a grab-bag of Dept/Branch/Section/UserType/combo-box
 * lookups that historically piggy-backed on {@link LoginService}.
 * <p>
 * Every endpoint returns HTTP 200 on the normal business path; success/failure is
 * signalled inside the JSON body via {@code message.resCode} (see
 * {@link com.ldb.iadoc.Mesage.Constant}), matching the convention used across the
 * rest of the API. Unexpected/unhandled errors are converted to a JSON 5xx/4xx
 * response by {@link com.ldb.iadoc.Exception.GlobalExceptionHandler} instead of
 * Spring's default HTML error page.
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class LoginController {

    private static final Logger log = LogManager.getLogger(LoginController.class);
    private static final String WATERMARK_IMAGE_URL = "https://dehome.ldblao.la/mobile/logo/ldb-logo.gif";

    private final LoginService loginService;

    /**
     * Watermarks every page of a PDF with a tiled logo. Kept as a REST endpoint for
     * ad-hoc/manual use; {@link #genPDF2} is the version used programmatically by
     * {@code DocumentImpl}.
     */
    @PostMapping("/Auth/genPDF")
    public void genPDF(String docPathLa, String fileName) throws Exception {
        File pdfFile = new File("log/temp.pdf");
        try {
            downloadFile(docPathLa, pdfFile);

            try (PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
                 PdfWriter writer = new PdfWriter(fileName);
                 PdfDocument pdfDoc = new PdfDocument(reader, writer)) {

                ImageData imageData = ImageDataFactory.create(new URL(WATERMARK_IMAGE_URL));
                applyWatermarkGrid(pdfDoc, imageData);
            }
        } finally {
            // Always clean up the downloaded temp file, even if watermarking failed.
            pdfFile.delete();
        }
    }

    /**
     * Same watermarking logic as {@link #genPDF}, streamed directly from a local
     * path or URL without an intermediate temp file. Not exposed as a REST endpoint
     * itself - called directly by {@code DocumentImpl} when generating shared PDFs.
     */
    public File genPDF2(String docPathLa, String outputPath) throws Exception {
        log.info("genPDF2: watermarking {} -> {}", docPathLa, outputPath);

        try (InputStream in = docPathLa.startsWith("http")
                ? new URL(docPathLa).openStream()
                : Files.newInputStream(Paths.get(docPathLa));
             PdfReader reader = new PdfReader(in);
             PdfWriter writer = new PdfWriter(outputPath);
             PdfDocument pdfDoc = new PdfDocument(reader, writer)) {

            ImageData imageData = ImageDataFactory.create(new URL(WATERMARK_IMAGE_URL));
            applyWatermarkGrid(pdfDoc, imageData);
        }

        return new File(outputPath);
    }

    private void applyWatermarkGrid(PdfDocument pdfDoc, ImageData imageData) {
        final int cols = 4;
        final int rows = 4;

        int totalPages = pdfDoc.getNumberOfPages();
        for (int p = 1; p <= totalPages; p++) {
            PdfPage page = pdfDoc.getPage(p);
            float pageW = page.getPageSize().getWidth();
            float pageH = page.getPageSize().getHeight();
            float cellW = pageW / cols;
            float cellH = pageH / rows;

            try (Canvas canvas = new Canvas(page, page.getPageSize())) {
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        Image img = new Image(imageData);
                        img.setOpacity(0.2f);
                        img.scaleToFit(cellW * 0.9f, cellH * 0.9f);
                        img.setRotationAngle(Math.toRadians(60));

                        float imgW = img.getImageScaledWidth();
                        float imgH = img.getImageScaledHeight();
                        float x = c * cellW + (cellW - imgW) / 2f;
                        float y = pageH - (r + 1) * cellH + (cellH - imgH) / 2f;

                        img.setFixedPosition(p, x, y);
                        canvas.add(img);
                    }
                }
            }
        }
    }

    private void downloadFile(String urlStr, File outputFile) throws Exception {
        URL url = new URL(urlStr);
        try (InputStream in = url.openStream();
             java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    //========================================Auth
    @PostMapping("/Auth/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) {
        log.info("POST /Auth/login - userName={}", loginReq.getUserName());
        return ResponseEntity.ok(loginService.LoginByUser(loginReq));
    }

    @PostMapping("/log/doLogByUser")
    public ResponseEntity<LoginRes> doLogByUser(@RequestBody login_log loginReq) {
        log.info("POST /log/doLogByUser");
        return ResponseEntity.ok(loginService.doLog(loginReq));
    }

    @PostMapping("/log/doLogByUserRead")
    public ResponseEntity<LoginRes> doLogByUserRead(@RequestBody login_log loginReq) {
        log.info("POST /log/doLogByUserRead");
        return ResponseEntity.ok(loginService.doLog(loginReq));
    }

    @PostMapping("/User/getShowUserInfo")
    public ResponseEntity<LoginRes> getShowUserInfo(@RequestBody LoginReq loginReq) {
        log.info("POST /User/getShowUserInfo");
        return ResponseEntity.ok(loginService.getShowUserInfo(loginReq));
    }

    @PostMapping("/log/getStatisticLogin")
    public ResponseEntity<VWStatisticRes> getStatisticLogin(@RequestBody VWStatisticReq loginReq) {
        log.info("POST /log/getStatisticLogin");
        return ResponseEntity.ok(loginService.getStatistic(loginReq));
    }

    @PostMapping("/log/dologStatistic")
    public ResponseEntity<VWStatisticRes> dologStatistic(@RequestBody VWStatisticReq loginReq) {
        log.info("POST /log/dologStatistic");
        return ResponseEntity.ok(loginService.dologStatistic(loginReq));
    }

    @PostMapping("/log/dologStatisticDetailsDoc")
    public ResponseEntity<VWStatisticLogRes> dologStatisticDetailsDoc(@RequestBody VWStatisticReq loginReq) {
        log.info("POST /log/dologStatisticDetailsDoc");
        return ResponseEntity.ok(loginService.dologStatisticDetailsDoc(loginReq));
    }

    @PostMapping("/log/dologStatisticDetailsLogin")
    public ResponseEntity<VWStatisticLogRes> dologStatisticDetailsLogin(@RequestBody VWStatisticReq loginReq) {
        log.info("POST /log/dologStatisticDetailsLogin");
        return ResponseEntity.ok(loginService.dologStatisticDetailsLog(loginReq));
    }

    @PostMapping("/UserType/getUserType")
    public ResponseEntity<UserTypeRes> getUserType() {
        log.info("POST /UserType/getUserType");
        return ResponseEntity.ok(loginService.getUserType());
    }

    @PostMapping("/Section/getSections")
    public ResponseEntity<SectionRes> getSections(@RequestBody SectionReq sectionReq) {
        log.info("POST /Section/getSections");
        return ResponseEntity.ok(loginService.getSections(sectionReq));
    }

    @PostMapping("/Section/getSectionsData")
    public ResponseEntity<SectionRes> getSectionsData(@RequestBody SectionReq sectionReq) {
        log.info("POST /Section/getSectionsData");
        return ResponseEntity.ok(loginService.getSectionData(sectionReq));
    }

    @PostMapping("/Auth/Signup")
    public ResponseEntity<ReponeRes> signup(@RequestBody SignupReq signupReq) throws ParseException {
        log.info("POST /Auth/Signup - userName={}", signupReq.getUserName());
        return ResponseEntity.ok(loginService.Signup(signupReq));
    }

    @PostMapping("/Auth/UpdatesSignUp")
    public ResponseEntity<ReponeRes> updatesSignUp(@RequestBody SignupReq signupReq) throws ParseException {
        log.info("POST /Auth/UpdatesSignUp - userName={}", signupReq.getUserName());
        return ResponseEntity.ok(loginService.UpdatesSignUp(signupReq));
    }

    @PostMapping("/Auth/DelSignUp")
    public ResponseEntity<ReponeRes> delSignUp(@RequestBody SignupReq signupReq) throws ParseException {
        log.info("POST /Auth/DelSignUp - userName={}", signupReq.getUserName());
        return ResponseEntity.ok(loginService.DelSignUp(signupReq));
    }

    @PostMapping("/Auth/chagePassword")
    public ResponseEntity<LoginChangPwdRes> chagePassword(@RequestBody LoginReq loginReq) {
        log.info("POST /Auth/chagePassword - oldUserId={}", loginReq.getOldUserId());
        return ResponseEntity.ok(loginService.getOldInfoOldUser(loginReq));
    }

    //========================================Dept
    @PostMapping("/Dept/saveDept")
    public ResponseEntity<ReponeRes> saveDept(@RequestBody DeptReq deptReq) throws ParseException {
        log.info("POST /Dept/saveDept");
        return ResponseEntity.ok(loginService.saveDept(deptReq));
    }

    @PostMapping("/Dept/updateDept")
    public ResponseEntity<ReponeRes> updateDept(@RequestBody DeptReq deptReq) throws ParseException {
        log.info("POST /Dept/updateDept");
        return ResponseEntity.ok(loginService.updateDept(deptReq));
    }

    @PostMapping("/Dept/delDept")
    public ResponseEntity<ReponeRes> delDept(@RequestBody DeptReq deptReq) throws ParseException {
        log.info("POST /Dept/delDept");
        return ResponseEntity.ok(loginService.delDept(deptReq));
    }

    @PostMapping("/Dept/getDeptList")
    public ResponseEntity<DeptRes> getDeptList(@RequestBody DeptReq deptReq) {
        log.info("POST /Dept/getDeptList");
        return ResponseEntity.ok(loginService.getDeptList(deptReq));
    }

    //========================================Branch
    @PostMapping("/Branch/saveBranch")
    public ResponseEntity<ReponeRes> saveBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("POST /Branch/saveBranch");
        return ResponseEntity.ok(loginService.saveBranch(branchReq));
    }

    @PostMapping("/Branch/updateBranch")
    public ResponseEntity<ReponeRes> updateBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("POST /Branch/updateBranch");
        return ResponseEntity.ok(loginService.updateBranch(branchReq));
    }

    @PostMapping("/Branch/delBranch")
    public ResponseEntity<ReponeRes> delBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("POST /Branch/delBranch");
        return ResponseEntity.ok(loginService.delBranch(branchReq));
    }

    @PostMapping("/Branch/getBranchList")
    public ResponseEntity<BranchRes> getBranchList(@RequestBody BranchReq branchReq) {
        log.info("POST /Branch/getBranchList");
        return ResponseEntity.ok(loginService.getBranchList(branchReq));
    }

    @PostMapping("/Branch/getBranchListAll")
    public ResponseEntity<BranchRes> getBranchListAll() {
        log.info("POST /Branch/getBranchListAll");
        return ResponseEntity.ok(loginService.getBranchListAll());
    }

    @PostMapping("/Branch/getBranchListStatus")
    public ResponseEntity<BranchRes> getBranchListStatus(@RequestBody BranchReq branchReq) {
        log.info("POST /Branch/getBranchListStatus");
        return ResponseEntity.ok(loginService.getBranchListStatus(branchReq));
    }

    //========================================Section
    @PostMapping("/Section/SaveSection")
    public ResponseEntity<ReponeRes> saveSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("POST /Section/SaveSection");
        return ResponseEntity.ok(loginService.saveSection(sectionReq));
    }

    @PostMapping("/Section/updateSection")
    public ResponseEntity<ReponeRes> updateSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("POST /Section/updateSection");
        return ResponseEntity.ok(loginService.upDateSection(sectionReq));
    }

    @PostMapping("/Section/delSection")
    public ResponseEntity<ReponeRes> delSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("POST /Section/delSection");
        return ResponseEntity.ok(loginService.delSection(sectionReq));
    }

    //========================================Combo-box lookups
    @PostMapping("/Branch/getComboxBranch")
    public ResponseEntity<ComboBranchRes> getComboxBranch() {
        log.info("POST /Branch/getComboxBranch");
        return ResponseEntity.ok(loginService.getComboxBranch());
    }

    @PostMapping("/Branch/getComboxBranchStatus")
    public ResponseEntity<ComboBranchRes> getComboxBranchStatus() {
        log.info("POST /Branch/getComboxBranchStatus");
        return ResponseEntity.ok(loginService.getComboxBranchStatus());
    }

    @PostMapping("/Section/getComboxSections")
    public ResponseEntity<ComboSectionRes> getComboxSections(@RequestBody ComboSectionReq sectionReq) {
        log.info("POST /Section/getComboxSections - branchCode={}", Arrays.toString(sectionReq.getBranchCode()));
        return ResponseEntity.ok(loginService.getComboxSections(sectionReq));
    }

    @PostMapping("/User/getComboxUser")
    public ResponseEntity<ComboUserRes> getComboxUser(@RequestBody ComboUserReq loginReq) {
        log.info("POST /User/getComboxUser");
        return ResponseEntity.ok(loginService.getComboxUser(loginReq));
    }
}
