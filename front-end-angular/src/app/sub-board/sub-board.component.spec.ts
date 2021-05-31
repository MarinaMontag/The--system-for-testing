import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubBoardComponent } from './sub-board.component';

describe('SubBoardComponent', () => {
  let component: SubBoardComponent;
  let fixture: ComponentFixture<SubBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
