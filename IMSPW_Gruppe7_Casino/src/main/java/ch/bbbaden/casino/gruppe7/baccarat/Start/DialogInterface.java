/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;

/**
 *
 * @author lbaum
 */
public interface DialogInterface {
    
    public void showInfoPlayerWin();
    
    public void showInfoPlayerWinNatural();
    
    public void showInfoBankerWin();
    
    public void showInfoBankerWinNatural();
    
    public void showInfoTie();
    
    public void showInfoInsufficentBalance();
    
    public void showInfoEnterBet();
    
    public void showErrorInvalid();
    
    public void showErrorMismatch();
    
    public void showInfoEmptyStockpile();
    
    public void showInfoNoBetOn();
    
    public boolean showWarningExit();
}
