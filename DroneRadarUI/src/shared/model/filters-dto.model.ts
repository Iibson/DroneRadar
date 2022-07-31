import { CompareType } from './compare-type.enum';

export interface FilterDto {
  propertyName: string;
  compareType: CompareType;
  compareValues: string[];
}
