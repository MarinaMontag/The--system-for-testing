import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {
  @Input()result: number;
  @Input()maxResult: number;
  @Input()testName: string;
  @Output() restart = new EventEmitter<boolean>();
  @Output() backToTests = new EventEmitter<boolean>();
  constructor() { }
  ngOnInit(): void {
  }
  repassTest(): void{
    this.restart.emit(true);
  }
  goBackToTests(): void{
    this.backToTests.emit(true);
  }
}
