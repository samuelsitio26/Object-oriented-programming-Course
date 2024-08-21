# T00 Environment Setup

Sesi ini ditujukan untuk memeriksa kesiapan perangkat pendukung yang diperlukan dalam pembelajaran. Silakan untuk memperhatikan [FAQ](FAQ.md). Ketika JDK anda sudah ter-install dengan benar, maka anda dapat menguji melakukan verifikasi dengan menjalankan perintah `javac --version` atau `java --version` di command prompt.

## Task 01 (50 points)

Modifikasi kelas `t00.env.HelloWorld` dengan melakukan hal berikut:

1. Tuliskan NIM dan nama anda pada `@author` annotation.
2. Lengkapi kelas tersebut sehingga tampak seperti kode berikut.

```java
public class HelloWorld {
    public static void main(String[] _args) {
        System.out.printf("Hello, world!\n");
    }
}

```

1. Untuk mengkompilasi, silakan untuk terlebih dahulu melakukan change directory dengan perintah `cd` seperti berikut:

```bash
cd bin

```

Dengan perintah di atas, current directory anda saat ini adalah `bin/`.

2. Setelah itu anda baru dapat melakukan kompilasi dengan perintah `javac` seperti berikut:

```bash
javac ../src/t00/env/HelloWorld.java -d .

```

Perintah di atas akan mengkompilasi source code `../src/t00/env/HelloWorld.java` dan meletakkan hasil kompilasi (class file) pada current directory `bin/`, hal ini ditandari dengan flag `-d` diikuti dengan `.` yang merujuk pada current directory.

3. Masih dengan `bin/` sebagai current directory, selanjutnya coba eksekusi program dengan perintah berikut:

```bash
java t00.env.HelloWorld

```

Program akan memberi output sebagai berikut:

```bash
Hello, world!

```

4. Bila sudah sesuai, silakan commit dan push pekerjaan anda ke GHC. Setelah menyelesaikan bagian ini, anda akan memperoleh nilai `+20 pts`.

## Task 02 (50 points)

Modifikasi kelas `t00.env.HelloJava` dengan melakukan hal berikut:

1. Tuliskan NIM dan nama anda pada `@author` annotation.
2. Lengkapi kelas tersebut sehingga jika dieksekusi akan memberikan sebaris keluaran sebagai berikut.

```bash
Hello, Java!

```

3. Uji coba pekerjaan anda terlebih dahulu sebelum melakukan commit dan push ke GHC. Setelah menyelesaikan bagian ini, nilai anda akan bertambah `+30 pts`.

## Submissions

1. t00\env\HelloWorld.java
2. t00\env\HelloJava.java
3. t00\env\IO.java
4. changelog.txt
