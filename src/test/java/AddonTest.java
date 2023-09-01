package test.java;

import java.util.List;

import main.java.br.com.bikenow.model.dao.bicycle.BicycleDAO;
import main.java.br.com.bikenow.model.entity.bicycle.Addon;
import main.java.br.com.bikenow.model.entity.bicycle.Bicycle;
import main.java.br.com.bikenow.model.infra.db.DB;
import main.java.br.com.bikenow.model.service.bicycle.AddonService;

public class AddonTest {
  public static void main(String[] args) {
    
    // ? Criando AddonService
    AddonService addonService = new AddonService(DB.getOracleConnection());

    // * CREATE - Inserindo Addon
    Bicycle bicycle = new BicycleDAO(DB.getOracleConnection()).findById(5);
    Addon addon = new Addon(1, "Banco", "Caloi", 1050.50, "D", bicycle);

    //addonService.createAddon(addon);

    // ? READ - Listando todos
    List<Addon> addons = addonService.findAll();
    //addons.forEach(a -> System.out.println(a.toString()));

    // ? READ - Listando por AddonID
    Addon addonById = addonService.findById(addon.getId());
    // System.out.println(addonById.toString());

    // ? READ - Listando por OwnerCpf
    List<Addon> addonByCpf = addonService.findByBicycleId(addon.getOwner().getId());
    //addonByCpf.forEach(ac -> System.out.println(ac.toString()));
    
    // * UPDATE - Atualizando Addon
    addon.setName("Guidão");
    addon.setBrand("Revolution");
    // addonService.updateBicycle(addon);
    System.out.println(addon.toString());
    
    // ! DELETE - Deletando Addon
    addonService.deleteAddon(addonById);

  }
}
