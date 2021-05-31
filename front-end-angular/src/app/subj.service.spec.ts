import { TestBed } from '@angular/core/testing';

import { SubjService } from './subj.service';

describe('SubjService', () => {
  let service: SubjService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
