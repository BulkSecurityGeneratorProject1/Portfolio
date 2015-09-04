package eu.thinking_aloud.portfolio.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.thinking_aloud.portfolio.domain.Quicksort;
import eu.thinking_aloud.portfolio.repository.QuicksortRepository;
import eu.thinking_aloud.portfolio.web.rest.util.HeaderUtil;
import eu.thinking_aloud.portfolio.web.rest.util.QuickSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Quicksort.
 */
@RestController
@RequestMapping("/api")
public class QuicksortResource {

    private final Logger log = LoggerFactory.getLogger(QuicksortResource.class);

    @Inject
    private QuicksortRepository quicksortRepository;

    /**
     * POST  /quicksorts -> Create a new quicksort.
     */
    @RequestMapping(value = "/quicksorts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quicksort> create(@Valid @RequestBody Quicksort quicksort) throws URISyntaxException {
        log.debug("REST request to save Quicksort : {}", quicksort);
        if (quicksort.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new quicksort cannot already have an ID").body(null);
        }

        QuickSorter qs = new QuickSorter(quicksort.getUnsorted());
        quicksort.setSorted(qs.getSorted());
        quicksort.setExecutionTime(qs.getExecutionTime());

        Quicksort result = quicksortRepository.save(quicksort);
        return ResponseEntity.created(new URI("/api/quicksorts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("quicksort", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /quicksorts -> Updates an existing quicksort.
     */
    @RequestMapping(value = "/quicksorts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quicksort> update(@Valid @RequestBody Quicksort quicksort) throws URISyntaxException {
        log.debug("REST request to update Quicksort : {}", quicksort);
        if (quicksort.getId() == null) {
            return create(quicksort);
        }

        QuickSorter qs = new QuickSorter(quicksort.getUnsorted());
        quicksort.setSorted(qs.getSorted());
        quicksort.setExecutionTime(qs.getExecutionTime());

        Quicksort result = quicksortRepository.save(quicksort);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("quicksort", quicksort.getId().toString()))
                .body(result);
    }

    /**
     * GET  /quicksorts -> get all the quicksorts.
     */
    @RequestMapping(value = "/quicksorts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Quicksort> getAll() {
        log.debug("REST request to get all Quicksorts");
        return quicksortRepository.findAll();
    }

    /**
     * GET  /quicksorts/:id -> get the "id" quicksort.
     */
    @RequestMapping(value = "/quicksorts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quicksort> get(@PathVariable String id) {
        log.debug("REST request to get Quicksort : {}", id);
        return Optional.ofNullable(quicksortRepository.findOne(id))
            .map(quicksort -> new ResponseEntity<>(
                quicksort,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /quicksorts/:id -> delete the "id" quicksort.
     */
    @RequestMapping(value = "/quicksorts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Quicksort : {}", id);
        quicksortRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quicksort", id.toString())).build();
    }
}
