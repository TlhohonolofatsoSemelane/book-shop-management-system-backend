package auca.ac.bookshopsystem.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.bookshopsystem.model.Purchase;
import auca.ac.bookshopsystem.service.PurchaseService;

@RestController
@RequestMapping(value = "/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> savePurchase(@RequestParam UUID reservationId, @RequestBody Purchase purchase) {
        String returnedMessage = purchaseService.savePurchase(reservationId, purchase);

        if (returnedMessage.equals("Purchase saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else if (returnedMessage.equals("The reservation with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/customer/{customerNumber}")
    public ResponseEntity<List<Purchase>> getPurchasesOfCustomer(@PathVariable String customerNumber) {
        return new ResponseEntity<>(purchaseService.getPurchasesOfCustomer(customerNumber), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable UUID id) {
        String returnedMessage = purchaseService.deletePurchase(id);

        if (returnedMessage.equals("Purchase deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }
}
