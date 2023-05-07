package be.gold.agency.backendbill.services;

import be.gold.agency.backendbill.models.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class InvoiceService {

    private final JavaType listInvoiceType;
    private final ObjectMapper objectMapper;
    private final ITemplateEngine templateEngine;

    private final ApplicationContext applicationContext;

    public InvoiceService(ObjectMapper objectMapper, ITemplateEngine templateEngine, ApplicationContext applicationContext) {
        this.objectMapper = objectMapper;
        this.listInvoiceType = objectMapper.getTypeFactory().constructCollectionType(List.class, Invoice.class);
        this.templateEngine = templateEngine;
        this.applicationContext = applicationContext;
    }

    public byte[] generateInvoice(Invoice invoice) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("invoice", invoice);
        return generatePdfFromThymeleaf("invoice_template", params);

    }
    public byte[] generatePdfFromThymeleaf(String templateName, Map<String, Object> variables) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TemplateEngine templateEngine = new TemplateEngine();
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ClassLoaderTemplateResolver imageResolver = new ClassLoaderTemplateResolver();
        imageResolver.setPrefix("classpath:/static/images/");
        imageResolver.setSuffix("");
        imageResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine.addTemplateResolver(templateResolver);
        templateEngine.addTemplateResolver(imageResolver);

        // génération de l'HTML à partir du template Thymeleaf et des variables
        String html = templateEngine.process(templateName, new Context(Locale.FRENCH, variables));

        // conversion de l'HTML en PDF
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        pdf.setDefaultPageSize(PageSize.A4);
        HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), pdf, null);
        pdf.close();

        return outputStream.toByteArray();
    }


    public byte[] generateInvoiceList(List<Invoice> invoiceList) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        for (Invoice invoice : invoiceList) {
            byte[] pdfBytes = generateInvoice(invoice);
            ZipEntry zipEntry = new ZipEntry(invoice.getInvoiceNumber() + ".pdf");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(pdfBytes);
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
        return outputStream.toByteArray();
    }





    public List<Invoice> getInvoiceList(String invoiceListJson) throws IOException {
        return objectMapper.readValue(invoiceListJson, listInvoiceType);
    }

    public String getInvoiceListJson(List<Invoice> invoiceList) throws JsonProcessingException {
        return objectMapper.writeValueAsString(invoiceList);
    }

}
