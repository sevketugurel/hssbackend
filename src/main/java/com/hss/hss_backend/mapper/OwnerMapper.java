package com.hss.hss_backend.mapper;

import com.hss.hss_backend.dto.request.OwnerCreateRequest;
import com.hss.hss_backend.dto.request.OwnerUpdateRequest;
import com.hss.hss_backend.dto.response.OwnerResponse;
import com.hss.hss_backend.entity.Owner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OwnerMapper {

    public static Owner toEntity(OwnerCreateRequest request) {
        return Owner.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .build();
    }

    public static void updateEntity(Owner owner, OwnerUpdateRequest request) {
        if (request.getFirstName() != null) {
            owner.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            owner.setLastName(request.getLastName());
        }
        if (request.getPhone() != null) {
            owner.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            owner.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            owner.setAddress(request.getAddress());
        }
    }

    public static OwnerResponse toResponse(Owner owner) {
        return OwnerResponse.builder()
                .ownerId(owner.getOwnerId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .phone(owner.getPhone())
                .email(owner.getEmail())
                .address(owner.getAddress())
                .createdAt(owner.getCreatedAt())
                .updatedAt(owner.getUpdatedAt())
                .build();
    }

    public static List<OwnerResponse> toResponseList(List<Owner> owners) {
        return owners.stream()
                .map(OwnerMapper::toResponse)
                .collect(Collectors.toList());
    }
}
