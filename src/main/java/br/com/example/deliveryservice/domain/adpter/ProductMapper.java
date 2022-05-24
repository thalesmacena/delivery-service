package br.com.example.deliveryservice.domain.adpter;

import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.internal.dto.ProductPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(ignore = true, target = "createdDate")
    @Mapping(ignore = true, target = "updatedDate")
    @Mapping(ignore = true, target = "id")
    Product toEntity(ProductPayload product );

    ProductPayload toDTO(Product product);

}
