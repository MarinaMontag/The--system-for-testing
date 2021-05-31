import {Component, OnInit} from '@angular/core';
import {Role} from '../model/role';
import {User} from '../model/user';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import jwtDecode from 'jwt-decode';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerUserData = new User('', '', '', '', null);
  constructor(private authService: AuthService,
              private router: Router) { }
  ngOnInit(): void {
  }
  register(role: string): void{
    this.authService.registerUser(this.registerUserData).subscribe(
     res => {
       localStorage.setItem('token', res.token);
       this.router.navigate(['/subject']);
     },
     error => console.log(error)
   );
  }
}
