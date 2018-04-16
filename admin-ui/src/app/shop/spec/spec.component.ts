import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-spec',
  templateUrl: './spec.component.html',
  styleUrls: ['./spec.component.css']
})
export class SpecComponent implements OnInit {

  index = '1';

  constructor() { }

  ngOnInit() {
  }

  handle(index: string) {
    this.index = index;
  }
}
