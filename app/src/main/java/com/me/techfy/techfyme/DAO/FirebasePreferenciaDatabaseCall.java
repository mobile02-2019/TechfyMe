package com.me.techfy.techfyme.DAO;

import com.me.techfy.techfyme.DTO.PreferenciaDTO;

public interface FirebasePreferenciaDatabaseCall {

    void onDataChange(PreferenciaDTO preferenciaDTO);

    void onDataCanceled();

}
