import {Component, OnInit} from '@angular/core';
import {SubjService} from '../subj.service';
import {Subject} from '../model/subject';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sub-board',
  templateUrl: './sub-board.component.html',
  styleUrls: ['./sub-board.component.css']
})
export class SubBoardComponent implements OnInit {
  subjects: Subject[] = [];
  constructor(private service: SubjService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.service.getSubjects().subscribe(
      subjects => {
        this.subjects = subjects.subjectList;
        console.log(subjects);
        console.log(subjects.subjectList);
      },
      error => console.log(error)
    );
  }

}
