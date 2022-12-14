package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.VehicleDto;
import cu.edu.cujae.pweb.service.VehicleService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;
import java.util.UUID;


@Component //Le indica a spring es un componete registrado
@ManagedBean
@ViewScoped //Este es el alcance utilizado para trabajar con Ajax
public class ManageVehicleBean {
    private VehicleDto vehicleDto;
    private VehicleDto selectedVehicle;
    private List<VehicleDto> vehicles;

    /* @Autowired es la manera para inyectar una dependencia/clase anotada con @service en spring
     * Tener en cuenta que lo que se inyecta siempre es la interfaz y no la clase
     */
    @Autowired
    private VehicleService vehicleService;

    public ManageVehicleBean() {

    }

    //Se ejecuta al dar clic en el button Nuevo
    public void openNew() {
        this.selectedVehicle = new VehicleDto();
    }

    //Se ejecuta al dar clic en el button con el lapicito
    public void openForEdit() {

    }

    public void saveVehicle() {
        if (this.selectedVehicle.getVehicle_id() == null) {
            this.vehicleService.createVehicle(this.selectedVehicle);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_vehicle_added"); //Este code permite mostrar un mensaje exitoso (FacesMessage.SEVERITY_INFO) obteniendo el mensage desde el fichero de recursos, con la llave message_user_added
        }
        else {
            this.vehicleService.updateVehicle(this.selectedVehicle);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_vehicle_edited");
        }

        vehicles = vehicleService.getVehicles();
        PrimeFaces.current().executeScript("PF('manageVehicleDialog').hide()");//Este code permite cerrar el dialog cuyo id es manageUserDialog. Este identificador es el widgetVar
        PrimeFaces.current().ajax().update("form:dt-vehicles");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
    }

    public void deleteVehicle() {
        try {
            this.vehicleService.deleteVehicle(this.selectedVehicle.getVehicle_id());
            this.selectedVehicle = null;

            this.vehicles = vehicleService.getVehicles();

            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_vehicle_removed");
            PrimeFaces.current().ajax().update("form:dt-vehicles");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form

        } catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }

    }

    public VehicleDto getVehicleDto() {
        return vehicleDto;
    }

    public void setVehicleDto(VehicleDto vehicleDto) {
        this.vehicleDto = vehicleDto;
    }

    public VehicleDto getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(VehicleDto selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public List<VehicleDto> getVehicles() {
        this.vehicles = vehicleService.getVehicles();
        return this.vehicles;
    }

    public void setVehicles(List<VehicleDto> vehicles) {
        this.vehicles = vehicles;
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}