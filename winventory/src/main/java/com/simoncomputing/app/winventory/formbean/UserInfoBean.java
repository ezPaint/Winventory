package com.simoncomputing.app.winventory.formbean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.simoncomputing.app.winventory.bo.RoleBo;
import com.simoncomputing.app.winventory.bo.UserBo;
import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.BoException;

public class UserInfoBean extends BaseBean{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String firstName;
	private String lastName;
	private String email; 
	private String cellPhone;
	private String workPhone;
	private boolean isActive;
	private int roleId;
	private String roleName;
    private Map<String, Boolean> hasPermission;
	
	public UserInfoBean() {}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map<String, Boolean> getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Map<String, Boolean> hasPermission) {
        this.hasPermission = hasPermission;
    }

	/**
	 * Bind Form bean to an HTTP request from parameters
	 * @param request
	 */
	public void bind(HttpServletRequest request) {
		this.setUsername(request.getParameter("username"));
		this.setFirstName(request.getParameter("firstname"));
		this.setLastName(request.getParameter("lastname"));
	}
	
	/**
	 * Bind a UserInfoBean 
	 * @param user
	 */
	public void bindUser(User user) {
		this.setUsername(user.getUsername());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setCellPhone(user.getCellPhone());
		this.setWorkPhone(user.getWorkPhone());
		this.setActive(user.getIsActive());
		this.setRoleId(user.getRoleId());
		
		RoleBo roleBo = RoleBo.getInstance();
        Role role = new Role();
        try {
            // roleId must be cast to a long for read()
            role = roleBo.read(new Long(user.getRoleId()));
        } catch (BoException e) {
            e.printStackTrace();
        }
        roleName = role.getTitle();

        hasPermission = new HashMap<String, Boolean>();
        List<String> permissionsList = UserBo.getPermissionsByUser(user);
        for (String permission : permissionsList) {
            hasPermission.put(permission, true);
        }
        this.setHasPermission(hasPermission);
	}
}
