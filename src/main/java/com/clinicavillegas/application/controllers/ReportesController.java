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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinicavillegas.application.dto.CitaCanceladaDTO;
import com.clinicavillegas.application.dto.CitaDentistaDTO;
import com.clinicavillegas.application.dto.CitaSexoDTO;
import com.clinicavillegas.application.dto.CitaTipoTratamientoDTO;
import com.clinicavillegas.application.dto.UsuarioResponse;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Tratamiento;
import com.clinicavillegas.application.services.CitaService;
import com.clinicavillegas.application.services.DentistaService;
import com.clinicavillegas.application.services.ReporteService;
import com.clinicavillegas.application.services.TratamientoService;
import com.clinicavillegas.application.services.UsuarioService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TratamientoService tratamientoRepository;

    @Autowired
    private DentistaService dentistaService;

    @Autowired
    private ReporteService reporteService;


    @GetMapping("/sexo")
    public List<CitaSexoDTO> countCitasByDateAndSexo(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return citaService.countCitasByDateAndSexo(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/sexo/download")
    public void obtenerEstadisticasSexo(
        @RequestParam(name = "startDate", required = true) String startDate,        
        @RequestParam(name = "endDate", required = true) String endDate,
        @RequestParam(name = "usuarioId", required = true) Long usuarioId,
        HttpServletResponse response
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-por-tipo-tratamiento.pdf");
        
        Document document = new Document(PageSize.A4.rotate());
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        reporteService.agregarReporte(1L, usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
            
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas por sexo", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            document.add(new Paragraph("Citas desde el " + startDate + " hasta el " + endDate, FontFactory.getFont(FontFactory.HELVETICA, 16)));
            document.add(Chunk.NEWLINE);
            PdfPTable table = citaService.createTableCitasPorSexo(LocalDate.parse(startDate), LocalDate.parse(endDate));
            document.add(table);
            JFreeChart chart = citaService.createChartCitasPorSexo(LocalDate.parse(startDate), LocalDate.parse(endDate));
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

    @GetMapping("/tipo-tratamiento")
    public List<CitaTipoTratamientoDTO> countCitasByDateAndTipoTratamiento(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return citaService.countCitasByDateAndTipoTratamiento(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/tipo-tratamiento/download")
    public void obtenerEstadisticasTipoTratamiento(
        @RequestParam(name = "startDate", required = true) String startDate,        
        @RequestParam(name = "endDate", required = true) String endDate,
        @RequestParam(name = "usuarioId", required = true) Long usuarioId,
        HttpServletResponse response
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-por-tipo-tratamiento.pdf");
        
        Document document = new Document(PageSize.A4.rotate());
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        reporteService.agregarReporte(2L, usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
            
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas por tipo de tratamiento", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            document.add(new Paragraph("Citas desde el " + startDate + " hasta el " + endDate, FontFactory.getFont(FontFactory.HELVETICA, 16)));
            document.add(Chunk.NEWLINE);
            List<Tratamiento> tratamientos = tratamientoRepository.obtenerTratamientos();
            for (Tratamiento tratamiento : tratamientos) {
                PdfPTable table = citaService.createTableCitasPorTipoTratamiento(LocalDate.parse(startDate), LocalDate.parse(endDate), tratamiento.getId());
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Tratamiento: " + tratamiento.getNombre(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                document.add(Chunk.NEWLINE);
                document.add(table);
            }
            JFreeChart chart = citaService.createChartCitasPorTipoTratamiento(LocalDate.parse(startDate), LocalDate.parse(endDate));
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

    @GetMapping("/canceladas")
    public List<CitaCanceladaDTO> countCitasCanceladasByFecha(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return citaService.countCitasCanceladasByFecha(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/canceladas/download")
    public void obtenerEstadisticasCanceladas(
        @RequestParam(name = "startDate", required = true) String startDate,        
        @RequestParam(name = "endDate", required = true) String endDate,
        @RequestParam(name = "usuarioId", required = true) Long usuarioId,
        HttpServletResponse response
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-canceladas.pdf");
        
        Document document = new Document(PageSize.A4.rotate());
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        reporteService.agregarReporte(3L, usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
            
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas canceladas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            document.add(new Paragraph("Citas desde el " + startDate + " hasta el " + endDate, FontFactory.getFont(FontFactory.HELVETICA, 16)));
            document.add(Chunk.NEWLINE);
            PdfPTable table = citaService.createTableCitasCanceladas(LocalDate.parse(startDate), LocalDate.parse(endDate));
            document.add(table);
            JFreeChart chart = citaService.createChartCitasCanceladas(LocalDate.parse(startDate), LocalDate.parse(endDate));
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

    @GetMapping("/dentista")
    public List<CitaDentistaDTO> countCitasAtendidasPorDentista(
            @RequestParam("estado") String estado,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return citaService.countCitasAtendidasPorDentista(estado, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/dentista/download")
    public void obtenerEstadisticasPorDentista(
        @RequestParam(name = "startDate", required = true) String startDate,        
        @RequestParam(name = "endDate", required = true) String endDate,
        @RequestParam(name = "estado", required = true) String estado,
        @RequestParam(name = "usuarioId", required = true) Long usuarioId,
        HttpServletResponse response
    ) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=citas-por-dentista.pdf");
        
        Document document = new Document(PageSize.A4.rotate());
        UsuarioResponse usuario = usuarioService.obtenerClientePorId(usuarioId);
        reporteService.agregarReporte(4L, usuarioId);
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter.getInstance(document, out);
            document.open();
            
            Image logo = Image.getInstance("src\\main\\resources\\static\\logo.jpg");
            logo.scaleToFit(100, 100);
            document.add(logo);
            document.add(new Paragraph("Estadísticas de citas por dentista", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Usuario: " + usuario.getApellidoMaterno() + " " + usuario.getApellidoPaterno() + ", " + usuario.getNombres()));
            document.add(new Paragraph("Fecha: " + LocalDate.now().toString()));
            document.add(new Paragraph("Citas desde el " + startDate + " hasta el " + endDate, FontFactory.getFont(FontFactory.HELVETICA, 16)));
            document.add(Chunk.NEWLINE);
            List<Dentista> dentistas = dentistaService.obtenerDentistas();
            for (Dentista dentista : dentistas) {
                document.add(new Paragraph("Dentista: " + dentista.getUsuario().getApellidoPaterno() + " " + dentista.getUsuario().getApellidoMaterno() + ", " + dentista.getUsuario().getNombres(), FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD)));
                PdfPTable table = citaService.createTableCitasPorDentista(LocalDate.parse(startDate), LocalDate.parse(endDate), dentista.getId());
                document.add(table);
            }
            JFreeChart chart = citaService.createChartCitasPorDentista(LocalDate.parse(startDate), LocalDate.parse(endDate), estado);
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
