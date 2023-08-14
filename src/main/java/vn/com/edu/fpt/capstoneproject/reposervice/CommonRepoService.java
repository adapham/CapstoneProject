package vn.com.edu.fpt.capstoneproject.reposervice;

import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import vn.com.edu.fpt.capstoneproject.constant.Constant;
import vn.com.edu.fpt.capstoneproject.exception.ActionNotAllowAttemptException;
import vn.com.edu.fpt.capstoneproject.exception.NotFoundEntityException;
import vn.com.edu.fpt.capstoneproject.repository.CommonRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CommonRepoService<E, ID, R extends CommonRepository<E, ID>> {

    protected final R repo;
    protected final Logger log;

    public CommonRepoService(R repo) {
        log = LoggerFactory.getLogger(getClass());
        this.repo = repo;
    }

    public E save(E entity) {
        repo.save(entity);
        return entity;
    }

    public List<E> save(List<E> entities) {
        repo.saveAll(entities);
        return entities;
    }

    public Optional<E> get(ID id) {
        return repo.findById(id);
    }

    public List<E> get(List<ID> ids) throws ActionNotAllowAttemptException {
        if (ids.size() > 1000) {
            throw ActionNotAllowAttemptException.of("over   .size.allowed");
        }
        return repo.findByIdIn(ids);
    }

    public E getOrElseThrow(ID id) throws NotFoundEntityException {
        var ex = NotFoundEntityException.ofSupplier("id", notFoundMessage(), id);
        log.warn(ex.get().getMessage() + " "+id);
        return get(id)
                .orElseThrow(ex);
    }

    public E getThrow(ID id, String errorField) throws NotFoundEntityException {
        var ex = NotFoundEntityException.ofSupplier("id", notFoundMessage(), id);
        log.warn(ex.get().getMessage() + " "+id);
        return get(id)
                .orElseThrow(NotFoundEntityException.ofSupplier(errorField, notFoundMessage()));
    }

    public E getOrElseThrow(ID id, String message) throws NotFoundEntityException {
        var ex = NotFoundEntityException.ofSupplier("id", message, id);
        log.warn(ex.get().getMessage() + " "+id);
        return get(id)
                .orElseThrow(NotFoundEntityException.ofSupplier(message));
    }

    public E delete(E entity) {
        repo.delete(entity);
        return entity;
    }

    public E deleteIfExisted(ID id) {
        Optional<E> optional = get(id);
        if (optional.isEmpty()) {
            return null;
        }
        E entity = optional.get();
        delete(entity);
        repo.flush();
        return entity;
    }

    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        repo.deleteAllByIdInBatch(ids);
    }

    public Page<E> query(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<E> query(Order... orders) {
        return query(null, orders);
    }

    public Page<E> query(Integer offset, Integer limit, Order... orders) {
        return query(null, offset, limit, orders);
    }

    public Page<E> query(Predicate condition, Order... orders) {
        return query(condition, null, null, orders);
    }

    public Page<E> query(Predicate condition, Integer offset, Integer limit, Order... orders) {
        Pageable pageable = ofPageable(offset, limit, orders);
        return condition != null ? repo.findAll(condition, pageable) : repo.findAll(pageable);
    }

    public List<E> all(Order... orders) {
        return all(null, orders);
    }

    public List<E> all(Predicate condition, Order... orders) {
        List<E> result = new LinkedList<>();

        Page<E> page = query(condition, orders);
        while (page.hasContent()) {
            result.addAll(page.getContent());

            Pageable nextPageable = page.getPageable().next();
            page = Objects.nonNull(condition) ? repo.findAll(condition, nextPageable) : repo.findAll(nextPageable);
        }
        return result;
    }

    public E updateOnField(ID id, Consumer<E> fieldConsumer) throws NotFoundEntityException {
        E entity = getOrElseThrow(id);
        return update(entity, fieldConsumer);
    }

    public E update(E entity, Consumer<E> fieldConsumer) {
        fieldConsumer.accept(entity);
        save(entity);
        return entity;
    }

    public boolean exits(ID id) {
        return repo.existsById(id);
    }

    public List<E> getAll(List<ID> ids) {
        return repo.findAllById(ids);
    }

    public List<E> getAll() {
        return repo.findAll();
    }

    protected abstract String notFoundMessage();

    protected Pageable ofPageable(Integer page, Integer size, Order... orders) {
        if (page == null) {
            page = Constant.DEFAULT_OFFSET;
        }
        if (size == null) {
            size = Constant.DEFAULT_LIMIT;
        }

        Sort sort = orders == null ? Sort.unsorted() : Sort.by(orders);

        return PageRequest.of(page - 1, size, sort);
    }
}
