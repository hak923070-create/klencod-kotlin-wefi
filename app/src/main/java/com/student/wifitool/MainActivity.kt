
import 'package:flutter/material.dart';

void main() {
  runApp(const WiFiLab());
}

class WiFiLab extends StatelessWidget {
  const WiFiLab({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'WiFi Security Lab',
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color(0xFF101827),
        colorScheme: const ColorScheme.dark(
          primary: Color(0xFF16C79A),
        ),
      ),
      home: const LabHome(),
    );
  }
}

class LabHome extends StatefulWidget {
  const LabHome({super.key});

  @override
  State<LabHome> createState() => _LabHomeState();
}

class _LabHomeState extends State<LabHome> {
  final targetController = TextEditingController(
    text: 'DemoPass123',
  );

  final listController = TextEditingController(
    text: 'password\n12345678\nDemo123\nDemoPass123\nTest456',
  );

  List<String> results = [];
  bool finished = false;
  int matches = 0;

  void startSimulation() {
    final target = targetController.text;
    final words = listController.text
        .split('\n')
        .map((e) => e.trim())
        .where((e) => e.isNotEmpty)
        .take(100)
        .toList();

    if (target.isEmpty || words.isEmpty) {
      setState(() {
        results = ['يرجى إدخال البيانات أولاً'];
        finished = false;
      });
      return;
    }

    int found = 0;

    final report = <String>[];

    for (int i = 0; i < words.length; i++) {
      if (words[i] == target) {
        found++;
        report.add('${i + 1} - تطابق تجريبي');
      } else {
        report.add('${i + 1} - لا يوجد تطابق');
      }
    }

    setState(() {
      results = report;
      matches = found;
      finished = true;
    });
  }

  String passwordStrength(String value) {
    if (value.length < 8) return 'ضعيفة';
    if (value.length < 12) return 'متوسطة';

    final hasNumber = RegExp(r'[0-9]').hasMatch(value);
    final hasUpper = RegExp(r'[A-Z]').hasMatch(value);
    final hasLower = RegExp(r'[a-z]').hasMatch(value);
    final hasSymbol = RegExp(
      r'[^a-zA-Z0-9]',
    ).hasMatch(value);

    final score = [
      hasNumber,
      hasUpper,
      hasLower,
      hasSymbol,
    ].where((x) => x).length;

    return score >= 3 ? 'قوية' : 'متوسطة';
  }

  Widget makeField(
    String title,
    TextEditingController controller,
    int lines,
  ) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(height: 10),
        TextField(
          controller: controller,
          maxLines: lines,
          textDirection: TextDirection.ltr,
          style: const TextStyle(color: Colors.white),
          decoration: InputDecoration(
            filled: true,
            fillColor: const Color(0xFF202D40),
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(14),
              borderSide: BorderSide.none,
            ),
          ),
        ),
      ],
    );
  }

  @override
  void dispose() {
    targetController.dispose();
    listController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Directionality(
      textDirection: TextDirection.rtl,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('WiFi Security Lab'),
          centerTitle: true,
          backgroundColor: const Color(0xFF172438),
        ),
        body: SingleChildScrollView(
          padding: const EdgeInsets.all(18),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const Icon(
                Icons.wifi_lock,
                size: 75,
                color: Color(0xFF16C79A),
              ),
              const SizedBox(height: 10),
              const Text(
                'مختبر أمن شبكات Wi-Fi',
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              const Text(
                'محاكاة تعليمية محلية فقط',
                textAlign: TextAlign.center,
                style: TextStyle(color: Colors.grey),
              ),
              const SizedBox(height: 25),

              makeField(
                'كلمة مرور الشبكة الافتراضية',
                targetController,
                1,
              ),

              const SizedBox(height: 20),

              Text(
                'قوة كلمة المرور: ${passwordStrength(targetController.text)}',
                style: const TextStyle(
                  color: Color(0xFF16C79A),
                  fontSize: 16,
                ),
              ),

              const SizedBox(height: 22),

              makeField(
                'قائمة كلمات المرور التجريبية',
                listController,
                6,
              ),

              const SizedBox(height: 20),

              ElevatedButton.icon(
                onPressed: startSimulation,
                icon: const Icon(Icons.play_arrow),
                label: const Text('بدء المحاكاة'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFF16C79A),
                  foregroundColor: Colors.black,
                  padding: const EdgeInsets.all(16),
                ),
              ),

              const SizedBox(height: 20),

              if (finished)
                Card(
                  color: const Color(0xFF202D40),
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      children: [
                        const Text(
                          'تقرير المحاكاة',
                          style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 10),
                        Text('عدد النتائج: ${results.length}'),
                        Text('عدد التطابقات: $matches'),
                      ],
                    ),
                  ),
                ),

              const SizedBox(height: 10),

              ...results.map(
                (result) => Card(
                  color: const Color(0xFF202D40),
                  child: ListTile(
                    leading: Icon(
                      result.contains('تطابق')
                          ? Icons.check_circle
                          : Icons.info_outline,
                      color: result.contains('تطابق')
                          ? Colors.green
                          : Colors.orange,
                    ),
                    title: Text(result),
                  ),
                ),
              ),

              const SizedBox(height: 20),

              const Text(
                'ملاحظة: هذا التطبيق يحاكي المقارنة '
                'بين كلمات نصية داخل الهاتف فقط. '
                'لا يتصل بشبكات Wi-Fi ولا يختبر '
                'كلمات مرور حقيقية.',
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 13,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}