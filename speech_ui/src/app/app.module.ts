import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VoicedisplayComponent } from './components/voicedisplay/voicedisplay.component';
import {SafePipe} from './safe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    SafePipe,
    VoicedisplayComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }