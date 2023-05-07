package be.gold.agency.backendbill.controllers;

import be.gold.agency.backendbill.models.Invoice;
import be.gold.agency.backendbill.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(value = "/single", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateSingleInvoice(@RequestBody Invoice invoice) throws Exception {
        byte[] pdfBytes = invoiceService.generateInvoice(invoice);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoice.pdf\"");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
    }

    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateBatchInvoices(@RequestBody List<Invoice> invoiceList)  {
        byte[] pdfBytes = invoiceService.generateInvoiceList(invoiceList);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoice_list.pdf\"");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getInvoices(@RequestParam(name = "list") String invoiceListJson) throws IOException {
        List<Invoice> invoiceList = invoiceService.getInvoiceList(invoiceListJson);
        String invoiceListJsonResponse = invoiceService.getInvoiceListJson(invoiceList);
        return ResponseEntity.ok().body(invoiceListJsonResponse);
    }
}