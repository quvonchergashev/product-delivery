package com.example.productdelivery.controller;

import com.example.productdelivery.dto.RequestDto;
import com.example.productdelivery.entity.Requests;
import com.example.productdelivery.payload.ResponseApi;
import com.example.productdelivery.service.interfaces.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PreAuthorize(value = "hasAnyAuthority('REQUEST_READ')")
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        List<Requests> all = requestService.findAll();
        return ResponseEntity.ok(all);
    }

    @PreAuthorize(value = "hasAnyAuthority('REQUEST_READ')")
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Requests> byId = requestService.findById(id);
        return ResponseEntity.ok(byId.get());
    }

    @PostMapping("/add-request")
    @Transactional
    public ResponseEntity<String> addRequest(
            @RequestBody RequestDto requestDto
    ) {
        boolean response = requestService.save(requestDto);
        if (response) {
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody Requests requests){
        ResponseApi edit = requestService.edit(requests);
        if (edit.isSuccess()) return ResponseEntity.ok(edit);
        return ResponseEntity.status(409).body(edit);
    }

    @PostMapping("delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        ResponseApi responseApi = requestService.deleteById(id);
        if (responseApi.isSuccess()) return ResponseEntity.ok(responseApi);
        return ResponseEntity.status(409).body(responseApi);
    }

    @GetMapping("/get-user-requests/{userId}")
    public ResponseEntity<?> getUserRequests(@PathVariable Long userId){
        ResponseApi userRequests = requestService.getUserRequests(userId);
        if (userRequests.isSuccess()) return ResponseEntity.ok(userRequests);
        return ResponseEntity.status(409).body(userRequests);
    }




}
