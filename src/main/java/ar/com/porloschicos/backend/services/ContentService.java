package ar.com.porloschicos.backend.services;

import ar.com.porloschicos.backend.ContentType.ContentType;
import ar.com.porloschicos.backend.controller.Content.Exceptions.ExceptionContent;
import ar.com.porloschicos.backend.model.content.ContentDao;
import ar.com.porloschicos.backend.model.content.ContentDto;
import ar.com.porloschicos.backend.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentDao;

    public ContentDao getContentByIdAndType(Long id, String type) throws ExceptionContent {
        ContentDao content = contentDao.getContentDaoByIdIsAndTypeIsAndStatusTrue(id, ContentType.valueOf(type.toUpperCase()).getId());
        if (content == null) throw new ExceptionContent(ExceptionContent.ERROR_MESSAGE_CONTENT_NOT_FOUND);
        return content;
    }

    public ContentDao[] getAllContentByType(String type) throws ExceptionContent {
        ContentDao[] content = contentDao.getAllByTypeAndStatusTrue(ContentType.valueOf(type.toUpperCase()).getId());
        if (content == null) throw new ExceptionContent(ExceptionContent.ERROR_MESSAGE_CONTENT_NOT_FOUND);
        return content;
    }

    public Boolean deleteContentById(Long id, String type) throws ExceptionContent {
        ContentDao content = contentDao.getContentDaoByIdIsAndTypeIsAndStatusTrue(id, ContentType.valueOf(type.toUpperCase()).getId());
        if (content == null) throw new ExceptionContent(ExceptionContent.ERROR_MESSAGE_CONTENT_NOT_FOUND);
        content.setStatus(false);
        contentDao.save(content);
        return true;
    }

    //TODO - need to finish this method - update is not working at all.
    public ContentDao update(ContentDto content) {

        ContentDao oldContent = contentDao.getById(content.getId());


        ContentDao newContent = new ContentDao(content);

        return contentDao.save(newContent);
    }

    public ContentDao save(ContentDto content) {
        ContentDao newContent = new ContentDao(
                content.getTitle(),
                content.getContent(),
                content.getImages(),
                content.getType()
        );

        return contentDao.save(newContent);
    }
}
