package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.CuentaDTORes;
import com.example.SpringSegurity.dto.dtoReq.CuentaDTOReq;

public interface CuentaService {

    CuentaDTORes createCuenta(CuentaDTOReq cuentaDTOReq);//crear cuenta

    CuentaDTORes updateCuenta(Long id,CuentaDTOReq cuentaDTOReq ); // actualizar cuenta

    CuentaDTORes deleteCuenta(Long id);//eliminar cuenta


}
