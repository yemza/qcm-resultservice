package com.qcm.resultservice.feign.impl;


import com.qcm.resultservice.dto.QcmDto;
import com.qcm.resultservice.feign.QcmFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QcmFeignImpl {

    @NonNull
    private QcmFeign qcmFeign;
    private Logger logger = LoggerFactory.getLogger(QcmFeignImpl.class);

    @CircuitBreaker(name="questionservice", fallbackMethod = "fallbackGetQcmById")
    public QcmDto getQcmById(Long qcmId){
        QcmDto qcmDto = qcmFeign.getQcmById(qcmId);
        return qcmDto;
    }

    public QcmDto fallbackGetQcmById(Long qcmId , Throwable throwable){
        logger.error("error : " + throwable.getMessage());
        return new QcmDto();
    }

}
