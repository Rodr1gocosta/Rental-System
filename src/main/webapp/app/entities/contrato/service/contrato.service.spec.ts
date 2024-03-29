import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContrato } from '../contrato.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../contrato.test-samples';

import { ContratoService, RestContrato } from './contrato.service';

const requireRestSample: RestContrato = {
  ...sampleWithRequiredData,
  periodo: sampleWithRequiredData.periodo?.toJSON(),
  contratoDataInicio: sampleWithRequiredData.contratoDataInicio?.toJSON(),
  contratoDataFinal: sampleWithRequiredData.contratoDataFinal?.toJSON(),
  contratoDataRetirada: sampleWithRequiredData.contratoDataRetirada?.toJSON(),
  contratoDataEntrega: sampleWithRequiredData.contratoDataEntrega?.toJSON(),
};

describe('Contrato Service', () => {
  let service: ContratoService;
  let httpMock: HttpTestingController;
  let expectedResult: IContrato | IContrato[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContratoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Contrato', () => {
      const contrato = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contrato).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contrato', () => {
      const contrato = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contrato).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Contrato', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Contrato', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Contrato', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContratoToCollectionIfMissing', () => {
      it('should add a Contrato to an empty array', () => {
        const contrato: IContrato = sampleWithRequiredData;
        expectedResult = service.addContratoToCollectionIfMissing([], contrato);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contrato);
      });

      it('should not add a Contrato to an array that contains it', () => {
        const contrato: IContrato = sampleWithRequiredData;
        const contratoCollection: IContrato[] = [
          {
            ...contrato,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContratoToCollectionIfMissing(contratoCollection, contrato);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contrato to an array that doesn't contain it", () => {
        const contrato: IContrato = sampleWithRequiredData;
        const contratoCollection: IContrato[] = [sampleWithPartialData];
        expectedResult = service.addContratoToCollectionIfMissing(contratoCollection, contrato);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contrato);
      });

      it('should add only unique Contrato to an array', () => {
        const contratoArray: IContrato[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contratoCollection: IContrato[] = [sampleWithRequiredData];
        expectedResult = service.addContratoToCollectionIfMissing(contratoCollection, ...contratoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contrato: IContrato = sampleWithRequiredData;
        const contrato2: IContrato = sampleWithPartialData;
        expectedResult = service.addContratoToCollectionIfMissing([], contrato, contrato2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contrato);
        expect(expectedResult).toContain(contrato2);
      });

      it('should accept null and undefined values', () => {
        const contrato: IContrato = sampleWithRequiredData;
        expectedResult = service.addContratoToCollectionIfMissing([], null, contrato, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contrato);
      });

      it('should return initial array if no Contrato is added', () => {
        const contratoCollection: IContrato[] = [sampleWithRequiredData];
        expectedResult = service.addContratoToCollectionIfMissing(contratoCollection, undefined, null);
        expect(expectedResult).toEqual(contratoCollection);
      });
    });

    describe('compareContrato', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContrato(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContrato(entity1, entity2);
        const compareResult2 = service.compareContrato(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContrato(entity1, entity2);
        const compareResult2 = service.compareContrato(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContrato(entity1, entity2);
        const compareResult2 = service.compareContrato(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
