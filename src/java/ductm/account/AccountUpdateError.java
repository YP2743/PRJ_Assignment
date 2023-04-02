/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.account;

/**
 *
 * @author truon
 */
public class AccountUpdateError {
    private String passwordUpdateError;

    public AccountUpdateError() {
    }
    

    /**
     * @return the passwordUpdateError
     */
    public String getPasswordUpdateError() {
        return passwordUpdateError;
    }

    /**
     * @param passwordUpdateError the passwordUpdateError to set
     */
    public void setPasswordUpdateError(String passwordUpdateError) {
        this.passwordUpdateError = passwordUpdateError;
    }
}
