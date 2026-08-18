package com.ldb.iadoc.Exception;

import com.ldb.iadoc.Contrller.LoginController;
import com.ldb.iadoc.Mesage.Constant;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.ReponeRes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

/**
 * Catches whatever escapes {@link LoginController}'s endpoints uncaught (i.e. bugs,
 * not expected business failures - those already return HTTP 200 with a
 * {@code message.resCode} of {@link Constant#codeError} etc.) and turns them into a
 * JSON {@link ReponeRes} body instead of Spring's default HTML error page.
 * <p>
 * Scoped to {@link LoginController} only, matching the current refactor's focus.
 */
@RestControllerAdvice(assignableTypes = LoginController.class)
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ReponeRes> handleParseException(ParseException e) {
        log.warn("Rejected request due to unparsable input: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody(Constant.msgError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReponeRes> handleUnexpected(Exception e) {
        log.error("Unhandled exception in LoginController", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody(Constant.msgError));
    }

    private ReponeRes errorBody(String resMgs) {
        Message message = new Message();
        message.setResCode(Constant.codeError);
        message.setResMgs(resMgs);
        return new ReponeRes(message);
    }
}
