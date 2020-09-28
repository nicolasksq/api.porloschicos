package ar.com.porloschicos.backend.controller.Content;

import ar.com.porloschicos.backend.controller.Content.Exceptions.ExceptionContent;
import ar.com.porloschicos.backend.controller.User.Exceptions.ExceptionAuth;
import ar.com.porloschicos.backend.model.content.ContentDto;
import ar.com.porloschicos.backend.services.ContentService;
import com.google.gson.Gson;
import com.oracle.tools.packager.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addContent(@RequestBody @Validated ContentDto content) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contentService.save(content));
        } catch (DataIntegrityViolationException e) {
            throw new ExceptionAuth(e.getMostSpecificCause().getMessage(), e);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    @RequestMapping(value = "/content/", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateContent(@RequestBody @Validated ContentDto content) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(contentService.update(content));
        } catch (DataIntegrityViolationException e) {
            throw new ExceptionAuth(e.getMostSpecificCause().getMessage(), e);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    @RequestMapping(value = "/content/{type}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getContent(@PathVariable("type") String type, @PathVariable("id") Long id) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(contentService.getContentByIdAndType(id,type));
        } catch (ExceptionContent e) {
            throw e;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    @RequestMapping(value = "/content/{type}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getContent(@PathVariable("type") String type) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(contentService.getAllContentByType(type));
        } catch (ExceptionContent e) {
            throw e;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    @RequestMapping(value = "/content/{type}/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteContent(@PathVariable("type") String type, @PathVariable("id") Long id) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(contentService.deleteContentById(id,type));
        } catch (ExceptionContent e) {
            throw e;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }
}


