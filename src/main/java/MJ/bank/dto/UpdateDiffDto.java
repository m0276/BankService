package MJ.bank.dto;

public record UpdateDiffDto(
    String propertyName,
    String prevContent,
    String currContent
) {

}
