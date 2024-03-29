package br.com.rentalsystem.locacoes.web.rest;

import br.com.rentalsystem.locacoes.repository.ContratoRepository;
import br.com.rentalsystem.locacoes.service.ContratoService;
import br.com.rentalsystem.locacoes.service.dto.ContratoDTO;
import br.com.rentalsystem.locacoes.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.rentalsystem.locacoes.domain.Contrato}.
 */
@RestController
@RequestMapping("/api/contratoes")
public class ContratoResource {

    private final Logger log = LoggerFactory.getLogger(ContratoResource.class);

    private static final String ENTITY_NAME = "contrato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratoService contratoService;

    private final ContratoRepository contratoRepository;

    public ContratoResource(ContratoService contratoService, ContratoRepository contratoRepository) {
        this.contratoService = contratoService;
        this.contratoRepository = contratoRepository;
    }

    /**
     * {@code POST  /contratoes} : Create a new contrato.
     *
     * @param contratoDTO the contratoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratoDTO, or with status {@code 400 (Bad Request)} if the contrato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContratoDTO> createContrato(@Valid @RequestBody ContratoDTO contratoDTO) throws URISyntaxException {
        log.debug("REST request to save Contrato : {}", contratoDTO);
        if (contratoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contrato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContratoDTO result = contratoService.save(contratoDTO);
        return ResponseEntity
            .created(new URI("/api/contratoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contratoes/:id} : Updates an existing contrato.
     *
     * @param id the id of the contratoDTO to save.
     * @param contratoDTO the contratoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratoDTO,
     * or with status {@code 400 (Bad Request)} if the contratoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> updateContrato(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContratoDTO contratoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Contrato : {}, {}", id, contratoDTO);
        if (contratoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContratoDTO result = contratoService.update(contratoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contratoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contratoes/:id} : Partial updates given fields of an existing contrato, field will ignore if it is null
     *
     * @param id the id of the contratoDTO to save.
     * @param contratoDTO the contratoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratoDTO,
     * or with status {@code 400 (Bad Request)} if the contratoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contratoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contratoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContratoDTO> partialUpdateContrato(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContratoDTO contratoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contrato partially : {}, {}", id, contratoDTO);
        if (contratoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContratoDTO> result = contratoService.partialUpdate(contratoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contratoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contratoes} : get all the contratoes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contratoes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContratoDTO>> getAllContratoes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Contratoes");
        Page<ContratoDTO> page = contratoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contratoes/:id} : get the "id" contrato.
     *
     * @param id the id of the contratoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> getContrato(@PathVariable("id") Long id) {
        log.debug("REST request to get Contrato : {}", id);
        Optional<ContratoDTO> contratoDTO = contratoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratoDTO);
    }

    /**
     * {@code DELETE  /contratoes/:id} : delete the "id" contrato.
     *
     * @param id the id of the contratoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable("id") Long id) {
        log.debug("REST request to delete Contrato : {}", id);
        contratoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
