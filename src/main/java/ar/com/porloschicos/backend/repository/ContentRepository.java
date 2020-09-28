package ar.com.porloschicos.backend.repository;

import ar.com.porloschicos.backend.model.content.ContentDao;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<ContentDao, Long> {
    ContentDao getById(Long id);
    ContentDao getByIdAndStatusTrue(Long id);
    ContentDao getContentDaosByIdIsAndStatusTrue(Long id);
    ContentDao getContentDaoByIdIsAndTypeIsAndStatusTrue(Long id,int type);
    ContentDao[] getAllByTypeAndStatusTrue(int type);
}
