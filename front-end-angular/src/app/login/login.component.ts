import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from '../model/user';
import {AuthService} from '../auth.service';
import {Role} from '../model/role';
import {Router} from '@angular/router';
import jwtDecode from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginUserData = new User('', '', '', '', null);
  constructor(private auth: AuthService,
              private router: Router) { }
  ngOnInit(): void {
  }

  login(): void {
    this.auth.loginUser(this.loginUserData).subscribe(
      res => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/subject']);
      },
      error => {
        console.log(error);
        if (error.statusText === 'Unknown Error') {
          console.log('Unknown Error');
        }
        this.router.navigate(['/register']);
      }
    );
  }
}
