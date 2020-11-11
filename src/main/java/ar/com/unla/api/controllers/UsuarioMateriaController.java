package ar.com.unla.api.controllers;

import ar.com.unla.api.dtos.response.MateriasInscriptasDTO;
import ar.com.unla.api.models.response.ApplicationResponse;
import ar.com.unla.api.models.response.ErrorResponse;
import ar.com.unla.api.models.swagger.usuariomateria.SwaggerUsuarioMateriasInscriptasOK;
import ar.com.unla.api.services.UsuarioMateriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Usuario-Materia controller", description = "CRUD UsuarioMateria")
@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios-materias")
public class UsuarioMateriaController {

    @Autowired
    private UsuarioMateriaService usuarioMateriaService;

    @GetMapping(path = "/materias-inscriptas")
    @ApiOperation(value = "Se encarga de traer una lista de materias con un flag que indique en "
            + "cual esta inscripta el usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message =
                            "Materias con flag de inscripción por usuario"
                                    + "encontradas",
                            response =
                                    SwaggerUsuarioMateriasInscriptasOK.class),
                    @ApiResponse(code = 400, message = "Request incorrecta al buscar una lista de"
                            + " materias con flag de inscripción por usuario", response =
                            ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error interno al buscar una lista de materias con flag de "
                                    + "inscripción por usuario",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse<List<MateriasInscriptasDTO>> getSubjectsWithInscriptionFlag(
            @RequestParam(name = "idUsuario")
            @NotNull(message = "El parámetro idUsuario no esta informado.")
            @ApiParam(required = true) Long idUsuario) {
        return new ApplicationResponse<>(
                usuarioMateriaService.findSubjectsAccordingRole(idUsuario), null);
    }


    @DeleteMapping
    @ApiOperation(value = "Se encarga eliminar una relacion de usuario y materia por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "UsuarioMateria eliminado"),
                    @ApiResponse(code = 400, message =
                            "Request incorrecta al eliminar un UsuarioMateria por su id",
                            response = ErrorResponse.class),
                    @ApiResponse(code = 500, message =
                            "Error al intentar eliminar un UsuarioMateria por su id",
                            response = ErrorResponse.class)
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam(name = "idUsuarioMateria")
            @NotNull(message = "El parámetro idUsuarioMateria no esta informado.")
            @ApiParam(required = true) Long id) {
        usuarioMateriaService.delete(id);
    }
}
