package componente;

import org.zkoss.zul.Messagebox;

public class Mensaje {
	public static String claveSYNoEsta = "El Codigo de Producto no Existe.";
	public static String claveRTNoEsta = "El Codigo Definido por el Usuario no Existe.";
	public static String guardado = "Registro Guardado Exitosamente.";
	public static String claveUsada = "La Clave ha sido Usada por Otro Registro.";
	public static String camposVacios = "Debe Llenar Todos lo Campos Requeridos.";
	public static String noSeleccionoItem = "No ha seleccionado ningun Item";
	public static String noHayRegistros = "No se Encontraron Registros";
	public static String editarSoloUno = "Solo puede Editar un Item a la vez, "
	+ "Seleccione un (1) solo Item y Repita la Operacion";
	public static String deseaEliminar = "�Desea Eliminar el Registro?";
	public static String eliminado = "Registro Eliminado Exitosamente";
	public static String estaEditando = "No ha culminado la Edicion, �Desea Continuar Editando?";
	public static String noSeleccionoRegistro = "No ha seleccionado ningun Registro";
	public static String exportar = "�Desea exportar los datos de la lista a formato CSV?";
	public static String enUso = "La interfaz esta siendo usada";
	public static String articuloNoExiste = "El Codigo del Articulo no Existe.";
	public static String enviado = "La solicitud ha sido enviada Correctamente";
	public static String correoInvalido = "Formato de Correo No Valido";
	public static String usuarioNoRegistrado = "El Usuario no Esta Registrado.";
	public static String correoNoConcuerda= "El Correo no Concuerda con los Datos del Usuario.";
	public static String contrasennasNoCoinciden = "Las Contrase�as no Coinciden.";
	public static String estadoIncorrecto = "Alguna de las solicitudes no se encuentran en el estado correcto para ser cambiadas";
	public static String estadoIncorrectoRechazada = "No puede cambiar de estado las solicitudes canceladas o rechadas, verifique su seleccion";
	public static String estadoIncorrectoEdicion = "No puede cambiar de estado las solicitudes en edicion, para modificarlas presione el boton Ver Solicitud";
	public static String eliminacionFallida = "No puede eliminar este grupo";
	public static String noPermitido = "El tipo de archivo que ha seleccionado no esta permitido, solo archivos con extension .jpeg y .png son permitidos";
	public static String tamanioMuyGrande = "El archivo que ha seleccionado no cumple con los parametros establecidos de tama�o; minimo 500Kb y maximo 2Mb";
	public static String listaVacia = "Debe a�adir una referencia de pago para cada Solicitud";
	public static String noEliminar = "El Registro no se puede Eliminar, Esta siendo Usado";
	public static String telefonoInvalido = "Formato de Telefono No Valido";
	public static String faltaMarca = "Debe seleccionar una Marca para cada Recurso en la lista de Recursos";
	public static String listaVaciaMotivo = "Debe a�adir un motivo de Cancelacion o Rechazo para cada Solicitud";
	public static String faltaCampoLista = "Debe seleccionar una Talla y un Genero para cada uniforme Agregado";
	public static String reinicioContrasenna = "Se envio un Correo Indicando los datos del Usuario";
	
	
	public void mensajeInformacion(String msj) {
	      Messagebox.show(msj, "Informacion",
					Messagebox.OK, Messagebox.INFORMATION);
		}
	
	public void mensajeAlerta(String msj) {
	      Messagebox.show(msj, "Alerta",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}

		public void mensajeError(String msj) {
	      Messagebox.show(msj, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
}