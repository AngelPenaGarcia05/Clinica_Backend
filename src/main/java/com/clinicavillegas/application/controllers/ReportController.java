package com.clinicavillegas.application.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateReport(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        Document document = new Document();
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
        
            document.add(new Paragraph("Reporte Dinámico", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        
            JFreeChart chart = createChart();
            BufferedImage chartImage = chart.createBufferedImage(500, 400);
        
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", baos);
            baos.close();
        
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(baos.toByteArray());
            pdfImage.scaleToFit(500, 400);
            document.add(pdfImage);
        
            document.add(new Paragraph("Este es un ejemplo de reporte generado dinámicamente."));
            document.add(new Paragraph("Gracias por usar nuestros servicios."));
            document.close();
        }
    }

    private JFreeChart createChart() {
        // Crear un gráfico de barras simple
        CategoryDataset dataset = createDataset();
        return ChartFactory.createBarChart(
            "Gráfico de ejemplo", // Título
            "Categorías", // Etiqueta eje X
            "Valores", // Etiqueta eje Y
            dataset, // Dataset
            PlotOrientation.VERTICAL, // Orientación
            true, true, false // Leyenda, tooltips, URLs
        );
    }
    
    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(4, "Serie 1", "Categoría 1");
        dataset.addValue(3, "Serie 1", "Categoría 2");
        dataset.addValue(2, "Serie 2", "Categoría 1");
        dataset.addValue(5, "Serie 2", "Categoría 2");
        return dataset;
    }
}
