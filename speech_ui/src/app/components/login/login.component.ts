import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../service/authentication.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false

  constructor(private router: Router,
    private loginservice: AuthenticationService,private http:HttpClient) {

  }

  ngOnInit(): void {
  }

  checkLogin() {
    if (this.loginservice.authenticate(this.username, this.password)
    ) {
      this.router.navigate([''])
      this.invalidLogin = false
    } else
      this.invalidLogin = true
  }

}
