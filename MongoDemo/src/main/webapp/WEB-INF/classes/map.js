function () {
    if(/.*iphone.*/.test(this.message)) emit("iPhone", 1);
    if(/.*ipad.*/.test(this.message)) emit("iPad", 1);
    if(/.*ndroid.*/.test(this.message)) emit("Android", 1);
    if(/.*Linux.*/.test(this.message)) emit("Linux", 1);
    if(/.*msie.*/.test(this.message)) emit("Internet Explorer", 1);
    if(/.*curl.*/.test(this.message)) emit("Curl", 1);
    if(/.*afari.*/.test(this.message)) emit("Safari", 1);
    if(/.*hrome.*/.test(this.message)) emit("Chrome", 1);
    if(/.*pera.*/.test(this.message)) emit("Opera", 1);
}