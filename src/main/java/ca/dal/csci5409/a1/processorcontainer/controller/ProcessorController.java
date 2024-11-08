package ca.dal.csci5409.a1.processorcontainer.controller;

import ca.dal.csci5409.a1.processorcontainer.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.dal.csci5409.a1.processorcontainer.model.Request;
import ca.dal.csci5409.a1.processorcontainer.model.Response;
import ca.dal.csci5409.a1.processorcontainer.service.DataProcessor;


@RestController
@Slf4j
@RequestMapping(Constants.PROCESSENDPOINT)
public class ProcessorController {
    @PostMapping(Constants.COMPUTEENDPOINT)
    public ResponseEntity<Response> GetFileProcessor(@RequestBody Request req) throws Exception {
        log.info("Received request: {}", req);

        try {
            log.debug("Processing file: {} with product: {}", req.getFile(), req.getProduct());

            int productSum = DataProcessor.ParseDataSourceFile(req.getFile(), req.getProduct());
            log.info("Data processing completed. Product sum: {}", productSum);

            Response res = new Response();
            res.setSum(productSum);
            res.setFile(req.getFile());

            log.debug("Response created: {}", res);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("Error occurred while processing the request: {}", e.getMessage(), e);
            throw e;
        }
    }
}
