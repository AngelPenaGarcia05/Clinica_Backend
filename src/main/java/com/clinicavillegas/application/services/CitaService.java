package com.clinicavillegas.application.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.stream.Collectors;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.clinicavillegas.application.dto.CitaRequest;
import com.clinicavillegas.application.dto.CitaResponse;
import com.clinicavillegas.application.dto.CitasPorEstadoDTO;
import com.clinicavillegas.application.dto.CitasPorMesDTO;
import com.clinicavillegas.application.dto.CitasPorTratamientoDTO;
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

    public List<CitaResponse> obtenerCitas(Long usuarioId, Long dentistaId, String estado) {
        Specification<Cita> specs = CitaSpecifications.conUsuarioId(usuarioId)
                .and(CitaSpecifications.conDentistaId(dentistaId))
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

    public List<CitasPorMesDTO> getCitasPorMesYSexo(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mes no válido");
            
        }
        List<Object[]> resultados = citaRepository.countCitasByMonthAndSexo(year, month);

        return resultados.stream()
                .map(obj -> {
                    int mes = ((Number) obj[0]).intValue(); // Primer argumento: mes
                    Sexo sexo = Sexo.values()[((Number) obj[1]).intValue()]; // Segundo argumento: sexo
                    long total = ((Number) obj[2]).longValue(); // Tercer argumento: total
                    return new CitasPorMesDTO(mes, sexo, total);
                })
                .collect(Collectors.toList());
    }

    public List<CitasPorTratamientoDTO> getCitasPorTipoTratamiento(int year, int month) {
        List<Object[]> resultados = citaRepository.countCitasByMonthAndTipoTratamiento(year, month);
        return resultados.stream()
                .map(obj -> {
                    int mes = ((Number) obj[0]).intValue(); // Primer argumento: mes
                    String tipoTratamiento = ((String) obj[1]).toUpperCase(); // Segundo argumento: tipoTratamiento
                    long total = ((Number) obj[2]).longValue(); // Tercer argumento: total
                    return new CitasPorTratamientoDTO(mes, tipoTratamiento, total);
                })
                .collect(Collectors.toList());
    }

    public List<CitasPorEstadoDTO> getCitasPorEstadoReporte(int year, String estado){
        List<Object[]> resultados = citaRepository.countCitasByMonthAndEstado(year, estado);
        return resultados.stream()
                .map(obj -> {
                    int mes = ((Number) obj[0]).intValue(); // Primer argumento: mes
                    long total = ((Number) obj[1]).longValue(); // Segundo argumento: total
                    return new CitasPorEstadoDTO(mes, total);
                })
                .collect(Collectors.toList());
    }

    public JFreeChart createChartCitasPorMesYSexo(int year, int month) {
        List<String> meses = List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitasPorMesDTO> resultados = getCitasPorMesYSexo(year, month);
        for (CitasPorMesDTO resultado : resultados) {
            dataset.addValue(resultado.getTotal(), meses.get(resultado.getMes() - 1) + "", resultado.getSexo().toString());
        }
        CategoryDataset cds = dataset;
        return ChartFactory.createBarChart(
            "Citas por sexo en el mes de " + meses.get(month - 1) + " " + year,
            meses.get(month - 1),
            "# de citas",
            cds,
            PlotOrientation.VERTICAL,
            true, true, false
        );
    }

    public JFreeChart createChartCitasPorTipoTratamiento(int year, int month) {
        List<String> meses = List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitasPorTratamientoDTO> resultados = getCitasPorTipoTratamiento(year, month);
        for (CitasPorTratamientoDTO resultado : resultados) {
            dataset.addValue(resultado.getTotal(), meses.get(resultado.getMes() - 1) + "", resultado.getTipoTratamiento());
        }
        CategoryDataset cds = dataset;
        return ChartFactory.createBarChart(
            "Citas por tipo de tratamiento en el mes de " + meses.get(month - 1) + " " + year,
            "Tratamientos",
            "# de citas",
            cds,
            PlotOrientation.VERTICAL,
            true, true, false
        );
    }

    public JFreeChart createChartCitasPorEstado(int year, String estado) {
        List<String> meses = List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CitasPorEstadoDTO> resultados = getCitasPorEstadoReporte(year, estado);
        for (int i = 0; i < resultados.size(); i++) {
            CitasPorEstadoDTO resultado = resultados.get(i);
            dataset.addValue(resultado.getTotal(), meses.get(resultado.getMes() - 1) + "", meses.get(resultado.getMes() - 1) + "");
            
        }
        CategoryDataset cds = dataset;
        return ChartFactory.createBarChart(
            "Citas por Estado " + estado.toLowerCase() + " " + year,
            "Categorías",
            "Valores",
            cds,
            PlotOrientation.VERTICAL,
            true, true, false
        );
    }
}
