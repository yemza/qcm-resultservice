package com.qcm.resultservice.service.impl;

import com.qcm.resultservice.dto.QcmDto;
import com.qcm.resultservice.dto.ResultDto;
import com.qcm.resultservice.dto.UserDto;
import com.qcm.resultservice.entities.Result;
import com.qcm.resultservice.feign.impl.QcmFeignImpl;
import com.qcm.resultservice.mappers.ResultMapper;
import com.qcm.resultservice.repositories.ResultRepository;
import com.qcm.resultservice.service.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private ResultRepository resultRepository;
    private ResultMapper resultMapper;
    private QcmFeignImpl qcmFeignImpl;


    @Override
    public List<ResultDto> getAllQcm(Long userId) {
        List<Result> resultResponse = resultRepository.findAllByUserId(userId);

        return resultResponse.stream().
                map(result -> {
                    ResultDto resultDto = resultMapper.resultToResultDto(result);
                    QcmDto qcmDto = qcmFeignImpl.getQcmById(result.getQcmId());
                    resultDto.setQcm(qcmDto);
                    return resultDto;
                }).collect(Collectors.toList());
    }

    @Override
    public ResultDto getQcmByIdAndUserId(Long qcmId, Long userId) {
        Result resultResponse =  resultRepository.getQcmByIdAndUserId(qcmId, userId);
        ResultDto resultDtoResponse = resultMapper.resultToResultDto(resultResponse);
        QcmDto qcmDto = qcmFeignImpl.getQcmById(resultResponse.getQcmId());
        resultDtoResponse.setQcm(qcmDto);
        UserDto userDto = new UserDto();
        userDto.setId(resultResponse.getUserId());
        resultDtoResponse.setUser(userDto);
        return resultDtoResponse;
    }
}
