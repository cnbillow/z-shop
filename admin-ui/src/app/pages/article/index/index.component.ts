import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  index = 0;
  tabTitle = '新建';

  constructor() { }

  ngOnInit() {
  }

  handle(index: number) {
    this.index = index;
  }
}
