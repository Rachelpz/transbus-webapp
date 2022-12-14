package cu.edu.cujae.pweb.bean;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserDto;
import cu.edu.cujae.pweb.service.RoleService;
import cu.edu.cujae.pweb.service.UserService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;


@Component //Le indica a spring es un componete registrado
@ManagedBean
@ViewScoped //Este es el alcance utilizado para trabajar con Ajax
public class ManageUserBean {

    private UserDto userDto;
    private UserDto selectedUser;
    private List<UserDto> users;
    private Long[] selectedRoles;

    private List<RoleDto> roles;

    private Integer sizeUsers = 0;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public ManageUserBean() {

    }

    //Se ejecuta al dar clic en el button Nuevo
    public void openNew() {
        this.selectedUser = new UserDto();
        this.selectedRoles = null;
    }

    //Se ejecuta al dar clic en el button con el lapicito
    public void openForEdit() {
        this.selectedRoles = this.selectedUser.getRoles().stream().map(r -> r.getId()).toArray(Long[]::new);
    }

    //Se ejecuta al dar clic en el button dentro del dialog para salvar o registrar al usuario
    public void saveUser() {
        List<RoleDto> rolesToAdd = new ArrayList<RoleDto>();

        for (int i = 0; i < this.selectedRoles.length; i++) {
            rolesToAdd.add(roleService.getRoleById(selectedRoles[i]));
        }

        this.selectedUser.setRoles(rolesToAdd);

        if (this.selectedUser.getId() == null) {
            //register user
            String message = this.userService.createUser(this.selectedUser);

            if (!message.isEmpty())
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "user_exists");
            else
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_added"); //Este code permite mostrar un mensaje exitoso (FacesMessage.SEVERITY_INFO) obteniendo el mensage desde el fichero de recursos, con la llave message_user_added

        } else {
            this.selectedUser.setPassword("");
            //update user
            String message = this.userService.updateUser(this.selectedUser);
            if (!message.isEmpty())
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "user_exists");
            else
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_edited");
        }

        //load datatable again with new values
        this.users = userService.getUsers();
        this.sizeUsers = users.size();
//
//        for (UserDto user : users)
//            System.out.println("\nUser ID: " + user.getId() + " Username: " + user.getUsername() + " Roles: " + user.getRoles().size());

        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:dt-users");
    }

    //Permite eliminar un usuario
    public void deleteUser(String logged_user_id) {
        try {
            //delete user
            System.out.println("logged user id: " + logged_user_id);
            System.out.println("selected user id: " + this.selectedUser.getId());

            if (!logged_user_id.equals(this.selectedUser.getId())) {
                this.userService.deleteUser(this.selectedUser.getId());
                this.selectedUser = null;

                //load datatable again with new values
                this.users = userService.getUsers();

                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_user_deleted");
                PrimeFaces.current().ajax().update("form:dt-users");// Este code es para refrescar el componente con id dt-users que se encuentra dentro del formulario con id form
            } else {
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "delete_logged_user");
            }

        } catch (Exception e) {
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_ERROR, "message_error");
        }
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserDto> getUsers() {
        users = userService.getUsers();
        System.out.println("\nGet Users");
        for (UserDto user : users) {
            System.out.println("username: " + user.getUsername() + " roles: " + user.getRoles().size());
        }
        return users;
    }

    public void setUsers(List<UserDto> users) {

        this.users = users;
    }

    public Long[] getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(Long[] selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public List<RoleDto> getRoles() {
        roles = roleService.getRoles();
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public Integer getSizeUsers() {
        this.sizeUsers = userService.getUsersSize();
        return this.sizeUsers;
    }

    public void setSizeUsers(Integer sizeUsers) {
        this.sizeUsers = sizeUsers;
    }

}
