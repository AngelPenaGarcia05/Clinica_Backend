package com.clinicavillegas.application.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.CitasPorEstadoDTO;
import com.clinicavillegas.application.dto.CitasPorMesDTO;
import com.clinicavillegas.application.dto.CitasPorTratamientoDTO;
import com.clinicavillegas.application.dto.UsuarioResponse;
import com.clinicavillegas.application.services.CitaService;
import com.clinicavillegas.application.services.UsuarioService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/citas-por-mes-y-sexo")
    public ResponseEntity<List<CitasPorMesDTO>> obtenerEstadisticas(
            @RequestParam(name = "year", required = true) int year,
            @RequestParam(name = "month", required = true) int month
        ) {
        return ResponseEntity.ok(citaService.getCitasPorMesYSexo(year, month));
    }

    @GetMapping("/citas-por-mes-y-sexo/download")
    public void obtenerEstadisticasCitasPorMesYSexo(
            @RequestParam(name = "year", required = true) int year,
            @RequestParam(name = "month", required = true) int month,
            @RequestParam(name = "usuarioId", required = true) Long usuarioId,
            HttpServletResponse response
        ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-por-mes-y-sexo.pdf");
        
        Document document = new Document();
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
        
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas por estado", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            
            JFreeChart chart = citaService.createChartCitasPorMesYSexo(year, month);
            BufferedImage chartImage = chart.createBufferedImage(500, 400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", baos);
            baos.close();
        
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(baos.toByteArray());
            pdfImage.scaleToFit(500, 400);
            document.add(pdfImage);
            document.close();
        }
    }
    
    @GetMapping("/citas-por-tipo-tratamiento")
    public ResponseEntity<List<CitasPorTratamientoDTO>> obtenerEstadisticasPorTipoTratamiento(
            @RequestParam(name = "year", required = true) int year,
            @RequestParam(name = "month", required = true) int month
        ) {
        return ResponseEntity.ok(citaService.getCitasPorTipoTratamiento(year, month));
    }

    @GetMapping("/citas-por-tipo-tratamiento/download")
    public void obtenerEstadisticasCitasPorTipoTratamiento(
        @RequestParam(name = "year", required = true) int year,
        @RequestParam(name = "month", required = true) int month,
        @RequestParam(name = "usuarioId", required = true) Long usuarioId,
        HttpServletResponse response
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-por-tipo-tratamiento.pdf");
        
        Document document = new Document();
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
            
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas por estado", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            JFreeChart chart = citaService.createChartCitasPorTipoTratamiento(year, month);
            BufferedImage chartImage = chart.createBufferedImage(500, 400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", baos);
            baos.close();
            
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(baos.toByteArray());
            pdfImage.scaleToFit(500, 400);
            document.add(pdfImage);
            document.close();
        }
    }
        
    @GetMapping("/citas-por-estado")
    public ResponseEntity<List<CitasPorEstadoDTO>> obtenerEstadisticasPorEstado(
            @RequestParam(name = "year", required = true) int year,
            @RequestParam(name = "estado", required = true) String estado
        ) {
        return ResponseEntity.ok(citaService.getCitasPorEstadoReporte(year, estado));
    }

    @GetMapping("/citas-por-estado/download")
    public void obtenerEstadisticasCitasPorEstado(
            @RequestParam(name = "year", required = true) int year,
            @RequestParam(name = "estado", required = true) String estado,
            @RequestParam(name = "usuarioId", required = true) Long usuarioId,
            HttpServletResponse response
        ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-por-estado.pdf");
        
        Document document = new Document();
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas por estado", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            
            JFreeChart chart = citaService.createChartCitasPorEstado(year, estado);
            BufferedImage chartImage = chart.createBufferedImage(500, 400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", baos);
            baos.close();
            
            com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(baos.toByteArray());
            pdfImage.scaleToFit(500, 400);
            document.add(pdfImage);
            document.close();
        }
    }
}
