package com.clinicavillegas.application.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.stream.Collectors;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.CitaCanceladaDTO;
import com.clinicavillegas.application.dto.CitaDentistaDTO;
import com.clinicavillegas.application.dto.CitaReprogramarRequest;
import com.clinicavillegas.application.dto.CitaRequest;
import com.clinicavillegas.application.dto.CitaResponse;
import com.clinicavillegas.application.dto.CitaSexoDTO;
import com.clinicavillegas.application.dto.CitaTipoTratamientoDTO;
import com.clinicavillegas.application.dto.DentistaResponse;
import com.clinicavillegas.application.dto.ValidacionCitaRequest;
import com.clinicavillegas.application.models.Cita;
import com.clinicavillegas.application.models.Dentista;
import com.clinicavillegas.application.models.Sexo;
import com.clinicavillegas.application.models.Tratamiento;
import com.clinicavillegas.application.models.Usuario;
import com.clinicavillegas.application.repositories.CitaRepository;
import com.clinicavillegas.application.repositories.DentistaRepository;
import com.clinicavillegas.application.repositories.TipoDocumentoRepository;
import com.clinicavillegas.application.repositories.TratamientoRepository;
import com.clinicavillegas.application.repositories.UsuarioRepository;
import com.clinicavillegas.application.specifications.CitaSpecifications;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public List<CitaResponse> obtenerCitas(Long usuarioId, Long dentistaId, String estado, LocalDate fechaInicio,
            LocalDate fechaFin, Long tratamientoId, String sexo) {
        Specification<Cita> specs = CitaSpecifications.conUsuarioId(usuarioId)
                .and(CitaSpecifications.conDentistaId(dentistaId))
                .and(CitaSpecifications.conRangoFecha(fechaInicio, fechaFin))
                .and(CitaSpecifications.conTratamientoId(tratamientoId))
                .and(CitaSpecifications.conSexo(sexo))
                .and(CitaSpecifications.conEstado(estado));
        List<Cita> citas = citaRepository.findAll(specs);
        return citas.stream().map(cita -> CitaResponse.builder()
                .id(cita.getId())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .monto(cita.getMonto())
                .nombres(cita.getNombres())
                .apellidoPaterno(cita.getApellidoPaterno())
                .apellidoMaterno(cita.getApellidoMaterno())
                .tipoDocumento(cita.getTipoDocumento())
                .numeroIdentidad(cita.getNumeroIdentidad())
                .telefono(cita.getUsuario().getTelefono())
                .sexo(cita.getSexo().toString())
                .estado(cita.getEstado())
                .fechaNacimiento(cita.getFechaNacimiento())
                .dentista(DentistaResponse.builder()
                        .id(cita.getDentista().getId())
                        .nombres(cita.getDentista().getUsuario().getNombres())
                        .apellidoPaterno(cita.getDentista().getUsuario().getApellidoPaterno())
                        .apellidoMaterno(cita.getDentista().getUsuario().getApellidoMaterno())
                        .especializacion(cita.getDentista().getEspecializacion())
                        .nColegiatura(cita.getDentista().getNColegiatura())
                        .estado(cita.getDentista().isEstado())
                        .tipoDocumento(cita.getDentista().getUsuario().getTipoDocumento())
                        .numeroIdentidad(cita.getDentista().getUsuario().getNumeroIdentidad())
                        .sexo(cita.getDentista().getUsuario().getSexo().toString())
                        .fechaNacimiento(cita.getDentista().getUsuario().getFechaNacimiento().toString())
                        .telefono(cita.getDentista().getUsuario().getTelefono())
                        .build())
                .usuarioId(cita.getUsuario().getId())
                .tratamiento(cita.getTratamiento())
                .build()).toList();
    }

    public List<Cita> obtenerCitasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        return citaRepository.findByUsuario(usuario);
    }

    public List<Cita> obtenerCitasPorDentista(Long dentistaId) {
        Dentista dentista = dentistaRepository.findById(dentistaId).orElseThrow();
        return citaRepository.findByDentista(dentista);
    }

    public void agregarCita(CitaRequest citaRequest) {
        Cita cita = Cita.builder()
                .fecha(citaRequest.getFecha())
                .hora(citaRequest.getHora())
                .monto(citaRequest.getMonto())
                .nombres(citaRequest.getNombres())
                .apellidoPaterno(citaRequest.getApellidoPaterno())
                .apellidoMaterno(citaRequest.getApellidoMaterno())
                .estado("Pendiente")
                .tipoDocumento(tipoDocumentoRepository.findByAcronimo(citaRequest.getTipoDocumento()).orElseThrow())
                .numeroIdentidad(citaRequest.getNumeroIdentidad())
                .sexo(Sexo.valueOf(citaRequest.getSexo()))
                .fechaNacimiento(citaRequest.getFechaNacimiento())
                .dentista(dentistaRepository.findById(citaRequest.getDentistaId()).orElseThrow())
                .usuario(usuarioRepository.findById(citaRequest.getUsuarioId()).orElseThrow())
                .tratamiento(tratamientoRepository.findById(citaRequest.getTratamientoId()).orElseThrow())
                .build();
        citaRepository.save(cita);
    }

    public void actualizarCita(Long id, CitaRequest citaRequest) {
        Cita cita = citaRepository.findById(id).get();
        cita.setMonto(citaRequest.getMonto());
        cita.setHora(citaRequest.getHora());
        cita.setFecha(citaRequest.getFecha());
        cita.setNombres(citaRequest.getNombres());
        cita.setApellidoPaterno(citaRequest.getApellidoPaterno());
        cita.setApellidoMaterno(citaRequest.getApellidoMaterno());
        cita.setTipoDocumento(tipoDocumentoRepository.findByNombre(citaRequest.getTipoDocumento()).orElseThrow());
        cita.setNumeroIdentidad(citaRequest.getNumeroIdentidad());
        cita.setSexo(Sexo.valueOf(citaRequest.getSexo()));
        cita.setFechaNacimiento(citaRequest.getFechaNacimiento());
        cita.setDentista(dentistaRepository.findById(citaRequest.getDentistaId()).orElseThrow());
        cita.setUsuario(usuarioRepository.findById(citaRequest.getUsuarioId()).orElseThrow());
        cita.setTratamiento(tratamientoRepository.findById(citaRequest.getTratamientoId()).orElseThrow());
        citaRepository.save(cita);
    }

    public void atenderCita(Long id) {
        citaRepository.findById(id).ifPresent(cita -> {
            cita.setEstado("Atendida");
            citaRepository.save(cita);
        });
    }

    public void eliminarCita(Long id) {
        Cita cita = citaRepository.findById(id).get();
        cita.setEstado("Cancelada");
        citaRepository.save(cita);
    }

    public boolean validarDisponibilidad(ValidacionCitaRequest request) {
        LocalDate fecha = LocalDate.parse(request.getFecha());
        LocalTime hora = LocalTime.parse(request.getHora());

        List<Cita> citasDelDia = citaRepository
                .findAll(CitaSpecifications.conFecha(fecha)
                        .and(CitaSpecifications.conEstado("Pendiente"))
                        .and(CitaSpecifications.conDentistaId(request.getDentistaId())));

        Tratamiento tratamiento = tratamientoRepository.findById(request.getTratamientoId()).get();
        if (tratamiento == null) {
            throw new IllegalArgumentException("Tratamiento no encontrado");
        }

        Duration duracion = tratamiento.getDuracion();
        LocalTime horaFinPropuesta = hora.plus(duracion);

        for (Cita cita : citasDelDia) {
            LocalTime horaInicioExistente = cita.getHora();
            LocalTime horaFinExistente = horaInicioExistente.plus(cita.getTratamiento().getDuracion());

            boolean seCruza = (hora.isBefore(horaFinExistente) && horaFinPropuesta.isAfter(horaInicioExistente));

            if (seCruza) {
                return false;
            }
        }

        return true;
    }

    public List<CitaSexoDTO> countCitasByDateAndSexo(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        return citaRepository.countCitasByDateAndSexo(startDate, endDate)
                .stream()
                .map(result -> new CitaSexoDTO(Sexo.values()[((Number) result[0]).intValue()].toString(), ((Number) result[1]).longValue()))
                .collect(Collectors.toList());
    }

    public List<CitaTipoTratamientoDTO> countCitasByDateAndTipoTratamiento(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        return citaRepository.countCitasByDateAndTipoTratamiento(startDate, endDate)
                .stream()
                .map(result -> new CitaTipoTratamientoDTO((String) result[0], ((Number) result[1]).longValue()))
                .collect(Collectors.toList());
    }

    public List<CitaCanceladaDTO> countCitasCanceladasByFecha(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        return citaRepository.countCitasCanceladasByFecha(startDate, endDate)
                .stream()
                .map(result -> new CitaCanceladaDTO(LocalDate.parse(result[0].toString()), ((Number) result[1]).longValue()))
                .collect(Collectors.toList());
    }

    public List<CitaDentistaDTO> countCitasAtendidasPorDentista(String estado, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        return citaRepository.countCitasAtendidasPorDentista(estado, startDate, endDate)
                .stream()
                .map(result -> new CitaDentistaDTO(
                        ((Number) result[0]).longValue(),
                        (String) result[1],
                        (String) result[2],
                        ((Number) result[3]).longValue()))
                .collect(Collectors.toList());
    }

    public JFreeChart createChartCitasPorSexo(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitaSexoDTO> result = countCitasByDateAndSexo(startDate, endDate);
        for (CitaSexoDTO dto : result) {
            dataset.addValue(dto.getTotal(), dto.getSexo(), "");
        }
        CategoryDataset cds = dataset;
        JFreeChart chart = ChartFactory.createBarChart(
                "Citas por sexo",
                "Sexo",
                "Cantidad",
                cds,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                        org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                        TextAnchor.BOTTOM_CENTER
                )
        );
        return chart;
    }

    public PdfPTable createTableCitasPorSexo(LocalDate startDate, LocalDate endDate){
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        List<Cita> citas = citaRepository.findAll(CitaSpecifications.conRangoFecha(startDate, endDate)
                .and(CitaSpecifications.conEstado("Pendiente").or(CitaSpecifications.conEstado("Atendida"))));
        //Mostrar los datos de las citas en la tabla
        PdfPTable table = new PdfPTable(13);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(0);
        table.setSpacingAfter(0);

        //Cabecera de la tabla
        Font styleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
        table.addCell(new Phrase("Id", styleFont));
        table.addCell(new Phrase("Nombres", styleFont));
        table.addCell(new Phrase("Apellido Paterno", styleFont));
        table.addCell(new Phrase("Apellido materno", styleFont));
        table.addCell(new Phrase("Fecha", styleFont));
        table.addCell(new Phrase("Hora", styleFont));
        table.addCell(new Phrase("Tipo documento", styleFont));
        table.addCell(new Phrase("Numero identidad", styleFont));
        table.addCell(new Phrase("Sexo", styleFont));
        table.addCell(new Phrase("Estado", styleFont));
        table.addCell(new Phrase("Monto", styleFont));
        table.addCell(new Phrase("Tratamiento", styleFont));
        table.addCell(new Phrase("Dentista", styleFont));

        //Añadir filas a la tabla
        for (Cita cita : citas) {
            table.addCell(cita.getId().toString());
            table.addCell(cita.getNombres());
            table.addCell(cita.getApellidoPaterno());
            table.addCell(cita.getApellidoMaterno());
            table.addCell(cita.getFecha().toString());
            table.addCell(cita.getHora().toString());
            table.addCell(cita.getTipoDocumento().getNombre());
            table.addCell(cita.getNumeroIdentidad());
            table.addCell(cita.getSexo().toString());
            table.addCell(cita.getEstado());
            table.addCell("S/ " +cita.getMonto());
            table.addCell(cita.getTratamiento().getNombre());
            table.addCell(cita.getDentista().getUsuario().getApellidoPaterno() + ", " + cita.getDentista().getUsuario().getNombres().charAt(0));
        }
        return table;
    }

    public JFreeChart createChartCitasPorTipoTratamiento(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitaTipoTratamientoDTO> result = countCitasByDateAndTipoTratamiento(startDate, endDate);
        for (CitaTipoTratamientoDTO dto : result) {
            dataset.addValue(dto.getTotal(), dto.getTipoTratamiento(), "");
        }
        CategoryDataset cds = dataset;
        JFreeChart chart = ChartFactory.createBarChart(
                "Citas por tipo de tratamiento",
                "Tipo de tratamiento",
                "Cantidad",
                cds,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                        org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                        TextAnchor.BOTTOM_CENTER
                )
        );
        return chart;
    }

    public PdfPTable createTableCitasPorTipoTratamiento(LocalDate startDate, LocalDate endDate, Long tratamientoId){
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        List<Cita> citas = citaRepository.findAll(CitaSpecifications.conRangoFecha(startDate, endDate)
                .and(CitaSpecifications.conTratamientoId(tratamientoId))
                .and(CitaSpecifications.conEstado("Pendiente").or(CitaSpecifications.conEstado("Atendida"))));
        //Mostrar los datos de las citas en la tabla
        PdfPTable table = new PdfPTable(13);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(0);
        table.setSpacingAfter(0);

        //Cabecera de la tabla
        Font styleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
        table.addCell(new Phrase("Id", styleFont));
        table.addCell(new Phrase("Nombres", styleFont));
        table.addCell(new Phrase("Apellido Paterno", styleFont));
        table.addCell(new Phrase("Apellido materno", styleFont));
        table.addCell(new Phrase("Fecha", styleFont));
        table.addCell(new Phrase("Hora", styleFont));
        table.addCell(new Phrase("Tipo documento", styleFont));
        table.addCell(new Phrase("Numero identidad", styleFont));
        table.addCell(new Phrase("Sexo", styleFont));
        table.addCell(new Phrase("Estado", styleFont));
        table.addCell(new Phrase("Monto", styleFont));
        table.addCell(new Phrase("Tratamiento", styleFont));
        table.addCell(new Phrase("Dentista", styleFont));

        //Añadir filas a la tabla
        for (Cita cita : citas) {
            table.addCell(cita.getId().toString());
            table.addCell(cita.getNombres());
            table.addCell(cita.getApellidoPaterno());
            table.addCell(cita.getApellidoMaterno());
            table.addCell(cita.getFecha().toString());
            table.addCell(cita.getHora().toString());
            table.addCell(cita.getTipoDocumento().getNombre());
            table.addCell(cita.getNumeroIdentidad());
            table.addCell(cita.getSexo().toString());
            table.addCell(cita.getEstado());
            table.addCell("S/ " +cita.getMonto());
            table.addCell(cita.getTratamiento().getNombre());
            table.addCell(cita.getDentista().getUsuario().getApellidoPaterno() + ", " + cita.getDentista().getUsuario().getNombres().charAt(0));
        }
        return table;
    }
    
    public JFreeChart createChartCitasCanceladas(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitaCanceladaDTO> result = countCitasCanceladasByFecha(startDate, endDate);
        for (CitaCanceladaDTO dto : result) {
            dataset.addValue(dto.getTotal(), dto.getFecha().toString(), "");
        }
        CategoryDataset cds = dataset;
        JFreeChart chart = ChartFactory.createBarChart(
                "Citas canceladas",
                "Fecha",
                "Cantidad",
                cds,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                        org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                        TextAnchor.BOTTOM_CENTER
                )
        );
        return chart;
    }

    public PdfPTable createTableCitasCanceladas(LocalDate startDate, LocalDate endDate){
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        List<Cita> citas = citaRepository.findAll(CitaSpecifications.conRangoFecha(startDate, endDate)
                .and(CitaSpecifications.conEstado("Cancelada")));
        //Mostrar los datos de las citas en la tabla
        PdfPTable table = new PdfPTable(13);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(0);
        table.setSpacingAfter(0);

        //Cabecera de la tabla
        Font styleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
        table.addCell(new Phrase("Id", styleFont));
        table.addCell(new Phrase("Nombres", styleFont));
        table.addCell(new Phrase("Apellido Paterno", styleFont));
        table.addCell(new Phrase("Apellido materno", styleFont));
        table.addCell(new Phrase("Fecha", styleFont));
        table.addCell(new Phrase("Hora", styleFont));
        table.addCell(new Phrase("Tipo documento", styleFont));
        table.addCell(new Phrase("Numero identidad", styleFont));
        table.addCell(new Phrase("Sexo", styleFont));
        table.addCell(new Phrase("Estado", styleFont));
        table.addCell(new Phrase("Monto", styleFont));
        table.addCell(new Phrase("Tratamiento", styleFont));
        table.addCell(new Phrase("Dentista", styleFont));

        //Añadir filas a la tabla
        for (Cita cita : citas) {
            table.addCell(cita.getId().toString());
            table.addCell(cita.getNombres());
            table.addCell(cita.getApellidoPaterno());
            table.addCell(cita.getApellidoMaterno());
            table.addCell(cita.getFecha().toString());
            table.addCell(cita.getHora().toString());
            table.addCell(cita.getTipoDocumento().getNombre());
            table.addCell(cita.getNumeroIdentidad());
            table.addCell(cita.getSexo().toString());
            table.addCell(cita.getEstado());
            table.addCell("S/ " +cita.getMonto());
            table.addCell(cita.getTratamiento().getNombre());
            table.addCell(cita.getDentista().getUsuario().getApellidoPaterno() + ", " + cita.getDentista().getUsuario().getNombres().charAt(0));
        }
        return table;
    }
    
    public JFreeChart createChartCitasPorDentista(LocalDate startDate, LocalDate endDate, String estado) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitaDentistaDTO> result = countCitasAtendidasPorDentista(estado, startDate, endDate);
        for (CitaDentistaDTO dto : result) {
            dataset.addValue(dto.getTotal(), dto.getDentistaNombres(), "");
        }
        CategoryDataset cds = dataset;
        JFreeChart chart = ChartFactory.createBarChart(
                "Citas por dentista",
                "Nombres",
                "Cantidad",
                cds,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                        org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                        TextAnchor.BOTTOM_CENTER
                )
        );
        return chart;
    }

    public PdfPTable createTableCitasPorDentista(LocalDate startDate, LocalDate endDate, Long dentistaId){
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin");
        }
        List<Cita> citas = citaRepository.findAll(CitaSpecifications.conRangoFecha(startDate, endDate)
                .and(CitaSpecifications.conEstado("Pendiente").or(CitaSpecifications.conEstado("Atendida")))
                .and(CitaSpecifications.conDentistaId(dentistaId)));
        //Mostrar los datos de las citas en la tabla
        PdfPTable table = new PdfPTable(13);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(0);
        table.setSpacingAfter(0);

        //Cabecera de la tabla
        Font styleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
        table.addCell(new Phrase("Id", styleFont));
        table.addCell(new Phrase("Nombres", styleFont));
        table.addCell(new Phrase("Apellido Paterno", styleFont));
        table.addCell(new Phrase("Apellido materno", styleFont));
        table.addCell(new Phrase("Fecha", styleFont));
        table.addCell(new Phrase("Hora", styleFont));
        table.addCell(new Phrase("Tipo documento", styleFont));
        table.addCell(new Phrase("Numero identidad", styleFont));
        table.addCell(new Phrase("Sexo", styleFont));
        table.addCell(new Phrase("Estado", styleFont));
        table.addCell(new Phrase("Monto", styleFont));
        table.addCell(new Phrase("Tratamiento", styleFont));
        table.addCell(new Phrase("Dentista", styleFont));

        //Añadir filas a la tabla
        for (Cita cita : citas) {
            table.addCell(cita.getId().toString());
            table.addCell(cita.getNombres());
            table.addCell(cita.getApellidoPaterno());
            table.addCell(cita.getApellidoMaterno());
            table.addCell(cita.getFecha().toString());
            table.addCell(cita.getHora().toString());
            table.addCell(cita.getTipoDocumento().getNombre());
            table.addCell(cita.getNumeroIdentidad());
            table.addCell(cita.getSexo().toString());
            table.addCell(cita.getEstado());
            table.addCell("S/ " +cita.getMonto());
            table.addCell(cita.getTratamiento().getNombre());
            table.addCell(cita.getDentista().getUsuario().getApellidoPaterno() + ", " + cita.getDentista().getUsuario().getNombres().charAt(0));
        }
        return table;
    }
    
    public void reprogramarCita(Long id, CitaReprogramarRequest request) {
        Cita cita = citaRepository.findById(id).get();
        cita.setHora(request.getHora());
        cita.setFecha(request.getFecha());
        citaRepository.save(cita);
    }
}
