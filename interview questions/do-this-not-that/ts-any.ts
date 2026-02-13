

//Option 1
interface Data {
  value: number;
}

class Sample {
  double(data: Data): number {
    return data.value * 2;
  }
}

//Option 2
class Sample1 {
  double(data: { value: number }): number {
    return data.value * 2;
  }
}
