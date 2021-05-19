package com.example.chocotest

class TestApplication : Application() {

    override fun createComponent(): TestAppComponent {
        return DaggerTestAppComponent
            .builder()
            .testAppModule(TestAppModule(this))
            .build()
    }

    override fun getComponent(): TestAppComponent {
        return super.getComponent() as TestAppComponent
    }
}