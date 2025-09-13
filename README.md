# Simple Note App

یک اپلیکیشن اندرویدی مدرن برای مدیریت یادداشت‌ها که با استفاده از Jetpack Compose و معماری MVVM توسعه یافته است.

## ویژگی‌های اصلی

- احراز هویت امن با JWT Token و تمدید خودکار
- ثبت‌نام و ورود کاربران
- مشاهده لیست یادداشت‌ها با pagination
- جستجو پیشرفته در یادداشت‌ها
- مشاهده جزئیات یادداشت
- مدیریت کامل یادداشت‌ها (اضافه کردن، ویرایش، حذف)
- عملیات bulk برای ایجاد چندین یادداشت همزمان
- فیلتر پیشرفته با تاریخ و محتوا
- مشاهده پروفایل کاربر
- تغییر رمز عبور
- خروج از حساب کاربری
- مکانیزم تجدید توکن احراز هویت
- کار offline-first با پایگاه داده محلی
- رابط کاربری مدرن با Material Design 3

## تکنولوژی‌های استفاده شده

- **Jetpack Compose** - رابط کاربری مدرن
- **MVVM Architecture** - معماری تمیز و قابل نگهداری
- **Room Database** - پایگاه داده محلی
- **Retrofit** - ارتباط با API
- **Hilt** - وابستگی تزریق
- **Coroutines & Flow** - برنامه‌نویسی غیرهمزمان
- **Paging 3** - صفحه‌بندی کارآمد

## ساختار پروژه

### Application Layer

#### `NoteApplication.kt`
کلاس اصلی اپلیکیشن که Hilt را راه‌اندازی می‌کند:

```kotlin
@HiltAndroidApp
class MyApplication : Application()
```

**توضیح کامل:**
- این کلاس نقطه ورود اصلی اپلیکیشن است
- `@HiltAndroidApp` annotation باعث می‌شود Hilt برای کل اپلیکیشن فعال شود
- Hilt تمام dependency injection را مدیریت می‌کند
- این کلاس در AndroidManifest.xml به عنوان کلاس اصلی اپلیکیشن معرفی شده است
- تمام ماژول‌های DI که در پروژه تعریف شده‌اند از اینجا شروع به کار می‌کنند

#### `MainActivity.kt`
فعالیت اصلی که Compose UI را راه‌اندازی می‌کند:

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleNoteTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}
```

**توضیح کامل:**
- `@AndroidEntryPoint` annotation باعث می‌شود Hilt بتواند وابستگی‌ها را به این Activity تزریق کند
- `enableEdgeToEdge()` اپلیکیشن را تا لبه‌های صفحه گسترش می‌دهد (full screen)
- `setContent` محتوای Compose UI را تنظیم می‌کند
- `SimpleNoteTheme` تم و رنگ‌های اپلیکیشن را اعمال می‌کند
- `AppNavHost` سیستم ناوبری را راه‌اندازی می‌کند
- `rememberNavController()` کنترلر ناوبری را ایجاد و حفظ می‌کند
- این Activity تنها نقطه ورود UI اپلیکیشن است

### Data Layer

#### Models (`data/models/`)

**`Note.kt`** - مدل اصلی یادداشت:
```kotlin
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: Int?,
    val title: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val creatorName: String,
    val creatorUsername: String
)
```

**توضیح کامل:**
- `@Entity` annotation این کلاس را به عنوان جدول پایگاه داده Room تعریف می‌کند
- `tableName = "notes"` نام جدول در پایگاه داده را مشخص می‌کند
- `@PrimaryKey` فیلد id را به عنوان کلید اصلی تعریف می‌کند
- `id: Int?` شناسه یکتا یادداشت (nullable چون ممکن است هنوز از سرور نیامده باشد)
- `title: String` عنوان یادداشت
- `description: String` محتوای کامل یادداشت
- `createdAt: String` زمان ایجاد یادداشت (فرمت ISO)
- `updatedAt: String` زمان آخرین ویرایش
- `creatorName: String` نام کامل سازنده یادداشت
- `creatorUsername: String` نام کاربری سازنده
- این مدل هم برای API و هم برای پایگاه داده محلی استفاده می‌شود

**`UserInfo.kt`** - اطلاعات کاربر:
```kotlin
data class UserInfo(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null
)
```

**توضیح کامل:**
- این کلاس اطلاعات کاربر را نگه می‌دارد
- `id: Int` شناسه یکتا کاربر در سیستم
- `username: String` نام کاربری برای ورود
- `email: String` ایمیل کاربر
- `firstName: String?` نام (اختیاری)
- `lastName: String?` نام خانوادگی (اختیاری)
- این اطلاعات از API دریافت می‌شود و در UI نمایش داده می‌شود
- برای نمایش پروفایل کاربر و تنظیمات استفاده می‌شود

**`NoteRequest.kt`** - درخواست ایجاد یادداشت:
```kotlin
data class NoteRequest(
    val title: String,
    val description: String
)
```

**توضیح کامل:**
- این کلاس برای ارسال درخواست ایجاد یا ویرایش یادداشت استفاده می‌شود
- `title: String` عنوان یادداشت که کاربر وارد می‌کند
- `description: String` محتوای یادداشت که کاربر می‌نویسد
- فقط فیلدهای ضروری را شامل می‌شود (بدون id و timestamp)
- برای API calls استفاده می‌شود
- زمان ایجاد و ویرایش توسط سرور تنظیم می‌شود

**`TokenObtainPairRequest.kt`** - درخواست احراز هویت:
```kotlin
data class TokenObtainPairRequest(
    val username: String,
    val password: String
)
```

**توضیح کامل:**
- این کلاس برای ارسال اطلاعات ورود به سرور استفاده می‌شود
- `username: String` نام کاربری که کاربر وارد می‌کند
- `password: String` رمز عبور کاربر
- این اطلاعات به API ارسال می‌شود تا JWT token دریافت شود
- برای عملیات login استفاده می‌شود
- اطلاعات حساس هستند و باید با HTTPS ارسال شوند

**`RegisterRequest.kt`** - درخواست ثبت‌نام:
```kotlin
data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null
)
```

**توضیح کامل:**
- این کلاس برای ارسال اطلاعات ثبت‌نام کاربر جدید استفاده می‌شود
- `username: String` نام کاربری انتخابی کاربر
- `password: String` رمز عبور انتخابی کاربر
- `email: String` ایمیل کاربر (برای بازیابی رمز عبور)
- `firstName: String?` نام (اختیاری)
- `lastName: String?` نام خانوادگی (اختیاری)
- فیلدهای اختیاری با `= null` مقدار پیش‌فرض دارند
- برای عملیات register استفاده می‌شود

**`PaginatedNoteList.kt`** - لیست صفحه‌بندی شده:
```kotlin
data class PaginatedNoteList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Note>
)
```

**توضیح کامل:**
- این کلاس پاسخ API برای دریافت لیست یادداشت‌ها را نشان می‌دهد
- `count: Int` تعداد کل یادداشت‌ها در سرور
- `next: String?` URL صفحه بعدی (null اگر صفحه آخر باشد)
- `previous: String?` URL صفحه قبلی (null اگر صفحه اول باشد)
- `results: List<Note>` لیست یادداشت‌های صفحه فعلی
- برای پیاده‌سازی pagination استفاده می‌شود
- عملکرد اپلیکیشن را بهبود می‌دهد (فقط بخشی از داده‌ها لود می‌شود)

**`ChangePasswordRequest.kt`** - درخواست تغییر رمز عبور:
```kotlin
data class ChangePasswordRequest(
    val old_password: String,
    val new_password: String
)

data class ChangePasswordResponse(
    val detail: String
)
```

**توضیح کامل:**
- `ChangePasswordRequest` برای ارسال درخواست تغییر رمز عبور
- `old_password: String` رمز عبور فعلی
- `new_password: String` رمز عبور جدید
- `ChangePasswordResponse` پاسخ سرور بعد از تغییر رمز عبور
- `detail: String` پیام پاسخ از سرور

**`BulkNoteRequest.kt`** - درخواست ایجاد چندین یادداشت:
```kotlin
data class BulkNoteRequest(
    val title: String,
    val description: String
)

data class BulkNoteResponse(
    val notes: List<Note>
)
```

**توضیح کامل:**
- `BulkNoteRequest` برای ایجاد چندین یادداشت در یک درخواست
- `title: String` عنوان یادداشت
- `description: String` محتوای یادداشت
- `BulkNoteResponse` لیست یادداشت‌های ایجاد شده
- برای بهبود عملکرد هنگام ایجاد چندین یادداشت

**`NoteFilterRequest.kt`** - درخواست فیلتر پیشرفته:
```kotlin
data class NoteFilterRequest(
    val title: String? = null,
    val description: String? = null,
    val updated__gte: String? = null, // Updated greater than or equal (ISO date)
    val updated__lte: String? = null, // Updated less than or equal (ISO date)
    val page: Int = 1,
    val page_size: Int = 20
)
```

**توضیح کامل:**
- `title: String?` فیلتر بر اساس عنوان
- `description: String?` فیلتر بر اساس محتوا
- `updated__gte: String?` یادداشت‌های به‌روزرسانی شده بعد از تاریخ مشخص
- `updated__lte: String?` یادداشت‌های به‌روزرسانی شده قبل از تاریخ مشخص
- `page: Int` شماره صفحه
- `page_size: Int` تعداد آیتم در هر صفحه

#### Database (`data/local/`)

**`AppDatabase.kt`** - پایگاه داده Room:
```kotlin
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
```

**توضیح کامل:**
- `@Database` annotation این کلاس را به عنوان پایگاه داده Room تعریف می‌کند
- `entities = [Note::class]` جدول‌های موجود در پایگاه داده را مشخص می‌کند
- `version = 1` نسخه پایگاه داده (برای migration استفاده می‌شود)
- `exportSchema = false` schema را export نمی‌کند (برای production)
- `abstract class` کلاس انتزاعی است و فقط DAO ها را تعریف می‌کند
- `noteDao(): NoteDao` DAO برای عملیات روی جدول notes
- Room خودکار کلاس پیاده‌سازی را ایجاد می‌کند
- Singleton pattern برای دسترسی به پایگاه داده استفاده می‌شود

**`NoteDao.kt`** - دسترسی به داده‌ها:
```kotlin
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getNotes(): PagingSource<Int, Note>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query ORDER BY updatedAt DESC")
    fun searchNotes(query: String): PagingSource<Int, Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
```

**توضیح کامل:**
- `@Dao` annotation این interface را به عنوان Data Access Object تعریف می‌کند
- `getNotes()`: تمام یادداشت‌ها را بر اساس تاریخ ویرایش (جدیدترین اول) برمی‌گرداند
- `PagingSource<Int, Note>` برای صفحه‌بندی خودکار استفاده می‌شود
- `searchNotes(query: String)`: جستجو در عنوان و محتوای یادداشت‌ها
- `LIKE :query` برای جستجوی جزئی استفاده می‌شود
- `ORDER BY updatedAt DESC` جدیدترین یادداشت‌ها را اول نمایش می‌دهد
- `@Insert(onConflict = OnConflictStrategy.REPLACE)` درج یادداشت جدید یا جایگزینی موجود
- `@Delete` حذف یادداشت از پایگاه داده
- `suspend` functions برای عملیات async در Coroutines

#### API (`data/remote/`)

**`ApiService.kt`** - رابط REST API:
```kotlin
interface ApiService {
    @POST("api/auth/register/")
    suspend fun register(@Body request: RegisterRequest): Register

    @POST("api/auth/token/")
    suspend fun login(@Body request: TokenObtainPairRequest): TokenObtainPair

    @GET("api/notes/")
    suspend fun getNotes(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20
    ): PaginatedNoteList

    @POST("api/notes/")
    suspend fun createNote(@Body request: NoteRequest): Note

    @PUT("api/notes/{id}/")
    suspend fun updateNote(@Path("id") id: Int, @Body request: NoteRequest): Note

    @DELETE("api/notes/{id}/")
    suspend fun deleteNote(@Path("id") id: Int): Response<Unit>
}
```

**توضیح کامل:**
- این interface تمام API endpoints را تعریف می‌کند
- **Authentication endpoints:**
  - `@POST("api/auth/register/")` ثبت‌نام کاربر جدید
  - `@POST("api/auth/token/")` دریافت JWT token برای ورود
  - `@POST("api/auth/token/refresh/")` تمدید JWT token
  - `@GET("api/auth/userinfo/")` دریافت اطلاعات کاربر
  - `@POST("api/auth/change-password/")` تغییر رمز عبور
- **Notes endpoints:**
  - `@GET("api/notes/")` دریافت لیست یادداشت‌ها با pagination
  - `@GET("api/notes/filter")` فیلتر پیشرفته یادداشت‌ها
  - `@POST("api/notes/")` ایجاد یادداشت جدید
  - `@POST("api/notes/bulk")` ایجاد چندین یادداشت همزمان
  - `@GET("api/notes/{id}/")` دریافت یادداشت خاص
  - `@PUT("api/notes/{id}/")` ویرایش کامل یادداشت
  - `@PATCH("api/notes/{id}/")` ویرایش جزئی یادداشت
  - `@DELETE("api/notes/{id}/")` حذف یادداشت
- `@Query` برای پارامترهای query string استفاده می‌شود
- `@Path("id")` برای پارامترهای URL استفاده می‌شود
- `@Body` برای ارسال داده در request body
- `suspend` functions برای عملیات async
- Retrofit خودکار implementation ایجاد می‌کند

**`AuthInterceptor.kt`** - مدیریت احراز هویت:
```kotlin
class AuthInterceptor @Inject constructor(
    private val authRepository: Provider<AuthRepository>,
    private val apiService: Provider<ApiService>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { authRepository.get().getAccessToken() }
        val requestBuilder = originalRequest.newBuilder()
        
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        
        // تمدید خودکار توکن در صورت انقضا
        if (response.code == 401) {
            val refreshToken = runBlocking { authRepository.get().getRefreshToken() }
            // منطق تمدید توکن
        }
        return response
    }
}
```

**توضیح کامل:**
- این کلاس OkHttp Interceptor است که برای هر درخواست HTTP اجرا می‌شود
- `@Inject constructor` وابستگی‌ها را از Hilt دریافت می‌کند
- `Provider<>` برای جلوگیری از circular dependency استفاده می‌شود
- `intercept()` method برای هر درخواست HTTP اجرا می‌شود
- `getAccessToken()` توکن JWT فعلی را از DataStore دریافت می‌کند
- `addHeader("Authorization", "Bearer $it")` توکن را به header درخواست اضافه می‌کند
- اگر پاسخ 401 (Unauthorized) باشد، توکن منقضی شده است
- `getRefreshToken()` توکن تمدید را دریافت می‌کند
- تمدید خودکار توکن باعث می‌شود کاربر دوباره login نکرده باشد

#### Repository (`data/repository/`)

**`AuthRepository.kt`** - مدیریت احراز هویت:
```kotlin
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) {
    suspend fun login(username: String, password: String): TokenObtainPair? {
        return try {
            val response = apiService.login(TokenObtainPairRequest(username, password))
            context.dataStore.edit {
                it[ACCESS_TOKEN] = response.access
                it[REFRESH_TOKEN] = response.refresh
            }
            response
        } catch (e: Exception) {
            null
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }
}
```

**توضیح کامل:**
- این Repository تمام عملیات احراز هویت را مدیریت می‌کند
- `@Inject constructor` وابستگی‌ها را از Hilt دریافت می‌کند
- `login()` method اطلاعات ورود را به API ارسال می‌کند
- `TokenObtainPairRequest` درخواست ورود را می‌سازد
- `context.dataStore.edit` توکن‌ها را در DataStore ذخیره می‌کند
- `ACCESS_TOKEN` و `REFRESH_TOKEN` کلیدهای ذخیره توکن‌ها هستند
- `try-catch` خطاهای شبکه را مدیریت می‌کند
- `isLoggedIn()` بررسی می‌کند آیا کاربر وارد شده است یا نه
- `getAccessToken()` توکن فعلی را از DataStore برمی‌گرداند
- DataStore برای ذخیره امن داده‌های حساس استفاده می‌شود

**`NoteRepository.kt`** - مدیریت یادداشت‌ها:
```kotlin
class NoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val noteDao: NoteDao
) {
    fun getNotes(searchQuery: String?): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                if (searchQuery.isNullOrEmpty()) {
                    noteDao.getNotes()
                } else {
                    noteDao.searchNotes("%$searchQuery%")
                }
            }
        ).flow
    }

    suspend fun createNote(noteRequest: NoteRequest): Note? {
        return try {
            val note = apiService.createNote(noteRequest)
            noteDao.insertNote(note)
            note
        } catch (e: Exception) {
            null
        }
    }
}
```

**توضیح کامل:**
- این Repository تمام عملیات یادداشت‌ها را مدیریت می‌کند
- `getNotes()` لیست یادداشت‌ها را با صفحه‌بندی برمی‌گرداند
- `Pager` برای پیاده‌سازی صفحه‌بندی استفاده می‌شود
- `PagingConfig(pageSize = 20)` هر صفحه 20 یادداشت دارد
- `enablePlaceholders = false` placeholder ها غیرفعال هستند
- `pagingSourceFactory` منبع داده‌ها را مشخص می‌کند
- اگر `searchQuery` خالی باشد، تمام یادداشت‌ها را برمی‌گرداند
- اگر `searchQuery` داشته باشد، جستجو در عنوان و محتوا انجام می‌شود
- `createNote()` یادداشت جدید را در سرور ایجاد می‌کند
- بعد از ایجاد در سرور، یادداشت در پایگاه داده محلی ذخیره می‌شود
- `try-catch` خطاهای شبکه را مدیریت می‌کند
- ترکیب API و پایگاه داده محلی برای عملکرد بهتر

#### Dependency Injection (`data/di/`)

**`AppModule.kt`** - ماژول اصلی:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}
```

**توضیح کامل:**
- `@Module` annotation این کلاس را به عنوان ماژول Hilt تعریف می‌کند
- `@InstallIn(SingletonComponent::class)` این ماژول در کل اپلیکیشن در دسترس است
- `object` کلاس singleton است (فقط یک instance)
- `@Provides` method های provider را مشخص می‌کند
- `@Singleton` این instance فقط یک بار ساخته می‌شود
- `@ApplicationContext` Context اپلیکیشن را تزریق می‌کند
- `provideContext()` Context اپلیکیشن را برای سایر کلاس‌ها فراهم می‌کند

**`DatabaseModule.kt`** - ماژول پایگاه داده:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "simplenote_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }
}
```

**توضیح کامل:**
- این ماژول تمام وابستگی‌های پایگاه داده را فراهم می‌کند
- `provideDatabase()` پایگاه داده Room را ایجاد می‌کند
- `Room.databaseBuilder()` builder pattern برای ساخت پایگاه داده
- `AppDatabase::class.java` کلاس پایگاه داده
- `"simplenote_db"` نام فایل پایگاه داده
- `provideNoteDao()` DAO را از پایگاه داده دریافت می‌کند
- `@Singleton` پایگاه داده فقط یک بار ساخته می‌شود
- Room خودکار thread safety و migration را مدیریت می‌کند

**`ApiModule.kt`** - ماژول API:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://simple-note.amirsalarsafaei.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
```

**توضیح کامل:**
- این ماژول تمام وابستگی‌های شبکه را فراهم می‌کند
- `provideRetrofit()` کلاینت Retrofit را ایجاد می‌کند
- `baseUrl()` URL پایه API را تنظیم می‌کند
- `client()` OkHttpClient را تنظیم می‌کند (شامل interceptor ها)
- `addConverterFactory()` Gson converter را اضافه می‌کند
- `provideApiService()` interface ApiService را پیاده‌سازی می‌کند
- `retrofit.create()` خودکار implementation ایجاد می‌کند
- `@Singleton` Retrofit instance فقط یک بار ساخته می‌شود
- تمام API calls از این instance استفاده می‌کنند

### Presentation Layer

#### ViewModels (`viewModel/`)

**`AuthViewModel.kt`** - مدیریت وضعیت احراز هویت:
```kotlin
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val token = authRepository.login(username, password)
            _authState.value = AuthState(token = token, isSuccess = token != null)
        }
    }

    fun register(username: String, email: String, password: String, firstName: String?, lastName: String?) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val response = authRepository.register(username, email, password, firstName, lastName)
            _authState.value = AuthState(isSuccess = response != null)
        }
    }
}
```

**توضیح کامل:**
- این ViewModel تمام منطق احراز هویت را مدیریت می‌کند
- `@HiltViewModel` annotation باعث می‌شود Hilt بتواند وابستگی‌ها را تزریق کند
- `@Inject constructor` AuthRepository را از Hilt دریافت می‌کند
- `_authState` state خصوصی ViewModel (MutableStateFlow)
- `authState` state عمومی که UI آن را مشاهده می‌کند (StateFlow)
- `AuthState` data class که وضعیت فعلی را نگه می‌دارد
- `login()` method اطلاعات ورود را به Repository ارسال می‌کند
- `viewModelScope.launch` coroutine برای عملیات async
- `isLoading = true` نشان می‌دهد در حال پردازش است
- `isSuccess` نشان می‌دهد عملیات موفق بوده یا نه
- `register()` method اطلاعات ثبت‌نام را پردازش می‌کند
- UI با مشاهده `authState` تغییرات را دریافت می‌کند

**`NoteViewModel.kt`** - مدیریت یادداشت‌ها:
```kotlin
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val notesFlow: Flow<PagingData<Note>> = _searchQuery.flatMapLatest {
        noteRepository.getNotes(it).cachedIn(viewModelScope)
    }

    private val _createState = MutableStateFlow(NoteCreateState())
    val createState: StateFlow<NoteCreateState> = _createState

    fun createNote(title: String, description: String) {
        viewModelScope.launch {
            val result = noteRepository.createNote(NoteRequest(title, description))
            _createState.value = if (result != null) {
                NoteCreateState(isSuccess = true)
            } else {
                NoteCreateState(error = "Failed to create note")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            noteRepository.syncNotes(query)
        }
    }
}
```

**توضیح کامل:**
- این ViewModel تمام منطق یادداشت‌ها را مدیریت می‌کند
- `_searchQuery` متن جستجوی فعلی را نگه می‌دارد
- `notesFlow` لیست یادداشت‌ها را با صفحه‌بندی برمی‌گرداند
- `flatMapLatest` فقط آخرین جستجو را پردازش می‌کند
- `cachedIn(viewModelScope)` cache را در scope ViewModel نگه می‌دارد
- `_createState` وضعیت ایجاد یادداشت جدید را نگه می‌دارد
- `createNote()` یادداشت جدید را ایجاد می‌کند
- `NoteRequest` درخواست ایجاد یادداشت را می‌سازد
- `updateSearchQuery()` متن جستجو را به‌روزرسانی می‌کند
- `syncNotes()` یادداشت‌ها را با سرور همگام‌سازی می‌کند
- UI با مشاهده state ها تغییرات را دریافت می‌کند

**`SettingViewModel.kt`** - مدیریت تنظیمات:
```kotlin
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    private val _changePasswordState = MutableStateFlow(ChangePasswordState())
    val changePasswordState: StateFlow<ChangePasswordState> = _changePasswordState.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch {
            _userState.value = UserState(isLoading = true)
            val user = authRepository.getUserInfo()
            _userState.value = UserState(user = user, isSuccess = user != null)
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _changePasswordState.value = ChangePasswordState(isLoading = true)
            val response = authRepository.changePassword(oldPassword, newPassword)
            _changePasswordState.value = ChangePasswordState(
                response = response,
                isSuccess = response != null
            )
        }
    }
}
```

**توضیح کامل:**
- این ViewModel مدیریت تنظیمات کاربر و تغییر رمز عبور را بر عهده دارد
- `UserState` وضعیت اطلاعات کاربر را نگه می‌دارد
- `ChangePasswordState` وضعیت تغییر رمز عبور را مدیریت می‌کند
- `getUserInfo()` اطلاعات کاربر را از سرور دریافت می‌کند
- `changePassword()` رمز عبور کاربر را تغییر می‌دهد
- `logout()` کاربر را از حساب خارج می‌کند

#### Navigation (`ui/navigation/`)

**`AppNavHost.kt`** - مدیریت ناوبری:
```kotlin
@Composable
fun AppNavHost(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val startDestination = if (authViewModel.isLoggedIn()) 
        NavItem.Home.route else NavItem.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavItem.Login.route) {
            LoginScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NavItem.Home.route) {
            HomeScreen(navController = navController, viewModel = hiltViewModel())
        }
        composable(NavItem.NoteEdit.route) { backStackEntry ->
            NoteEditScreen(
                navController = navController,
                noteId = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0,
                viewModel = hiltViewModel()
            )
        }
    }
}
```

**توضیح کامل:**
- این کامپوننت سیستم ناوبری کل اپلیکیشن را مدیریت می‌کند
- `hiltViewModel()` ViewModel را از Hilt دریافت می‌کند
- `isLoggedIn()` بررسی می‌کند آیا کاربر وارد شده است یا نه
- اگر کاربر وارد شده باشد، به صفحه Home می‌رود
- اگر کاربر وارد نشده باشد، به صفحه Login می‌رود
- `NavHost` container اصلی برای ناوبری است
- `composable()` هر صفحه را به عنوان composable تعریف می‌کند
- `backStackEntry.arguments` پارامترهای ارسالی به صفحه را دریافت می‌کند
- `getString("id")?.toIntOrNull() ?: 0` id یادداشت را از URL استخراج می‌کند
- اگر id وجود نداشته باشد، 0 (یادداشت جدید) استفاده می‌شود

#### Screens (`ui/screen/`)

**`LoginScreen.kt`** - صفحه ورود:
```kotlin
@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(NeutralWhite),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputField(
            labelText = "Email Address",
            value = email,
            onValueChange = { email = it }
        )
        
        CustomButton(
            text = "Login",
            onClick = { viewModel.login(email, password) }
        )
    }

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            navController.navigate(NavItem.Home.route)
        }
    }
}
```

**توضیح کامل:**
- این صفحه ورود کاربر را نمایش می‌دهد
- `remember { mutableStateOf("") }` state های محلی برای فیلدهای ورودی
- `collectAsState()` state ViewModel را مشاهده می‌کند
- `Column` layout عمودی برای قرار دادن عناصر
- `fillMaxSize()` تمام صفحه را پر می‌کند
- `background(NeutralWhite)` رنگ پس‌زمینه سفید
- `Arrangement.Center` عناصر را در مرکز قرار می‌دهد
- `Alignment.CenterHorizontally` تراز افقی مرکزی
- `InputField` فیلد ورودی ایمیل
- `CustomButton` دکمه ورود
- `LaunchedEffect` side effect برای ناوبری
- `loginState.isSuccess` بررسی موفقیت ورود
- `navController.navigate()` ناوبری به صفحه Home

**`HomeScreen.kt`** - صفحه اصلی:
```kotlin
@Composable
fun HomeScreen(navController: NavHostController, viewModel: NotesViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val notes = viewModel.notesFlow.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize().background(PrimaryBackground)) {
        if (notes.itemCount == 0) {
            EmptyState()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(notes.itemCount) { index ->
                    val note = notes[index]
                    if (note != null) {
                        NoteCard(
                            title = note.title,
                            content = note.description,
                            onClick = { navController.navigate("note/edit/${note.id}") }
                        )
                    }
                }
            }
        }
    }
}
```

**توضیح کامل:**
- این صفحه اصلی اپلیکیشن است که لیست یادداشت‌ها را نمایش می‌دهد
- `searchQuery` متن جستجوی فعلی را از ViewModel دریافت می‌کند
- `notesFlow.collectAsLazyPagingItems()` لیست یادداشت‌ها را با صفحه‌بندی دریافت می‌کند
- `Box` container اصلی صفحه
- `PrimaryBackground` رنگ پس‌زمینه اصلی اپلیکیشن
- `notes.itemCount == 0` بررسی خالی بودن لیست
- `EmptyState()` صفحه خالی را نمایش می‌دهد
- `LazyVerticalGrid` grid layout برای نمایش یادداشت‌ها
- `GridCells.Fixed(2)` 2 ستون ثابت
- `contentPadding` padding داخلی grid
- `items(notes.itemCount)` تعداد آیتم‌های grid
- `NoteCard` کارت نمایش هر یادداشت
- `onClick` با کلیک روی کارت به صفحه ویرایش می‌رود
- `navController.navigate()` ناوبری با id یادداشت

**`ChangePasswordScreen.kt`** - صفحه تغییر رمز عبور:
```kotlin
@Composable
fun ChangePasswordScreen(
    navController: NavHostController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    val changePasswordState by viewModel.changePasswordState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(NeutralWhite)
    ) {
        StatusBar()
        
        NavBar(
            title = "Change Password",
            onBackClick = { navController.navigate(NavItem.Setting.route) }
        )

        InputField(
            labelText = "Current Password",
            value = oldPassword,
            onValueChange = { oldPassword = it },
            isPassword = true
        )

        InputField(
            labelText = "New Password",
            value = newPassword,
            onValueChange = { newPassword = it },
            isPassword = true
        )

        CustomButton(
            text = "Change Password",
            onClick = { viewModel.changePassword(oldPassword, newPassword) }
        )
    }
}
```

**توضیح کامل:**
- این صفحه امکان تغییر رمز عبور کاربر را فراهم می‌کند
- `oldPassword` رمز عبور فعلی
- `newPassword` رمز عبور جدید
- `confirmPassword` تأیید رمز عبور جدید
- `changePasswordState` وضعیت تغییر رمز عبور را مشاهده می‌کند
- `InputField` فیلدهای ورودی رمز عبور
- `CustomButton` دکمه تغییر رمز عبور
- `LaunchedEffect` ناوبری بعد از موفقیت

#### Components (`ui/components/`)

**`NoteCard.kt`** - کارت نمایش یادداشت:
```kotlin
@Composable
fun NoteCard(
    title: String,
    content: String,
    color: NoteCardColor = NoteCardColor.Secondary,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(244.dp)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
        colors = CardDefaults.cardColors(containerColor = getCardColor(color))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = AppTypography.textBaseMedium)
            Text(text = content, style = AppTypography.textXsRegular)
        }
    }
}
```

**توضیح کامل:**
- این کامپوننت کارت نمایش هر یادداشت را می‌سازد
- `title: String` عنوان یادداشت
- `content: String` محتوای یادداشت
- `color: NoteCardColor` رنگ کارت (پیش‌فرض Secondary)
- `onClick: (() -> Unit)?` callback کلیک (اختیاری)
- `Card` Material Design 3 card component
- `fillMaxWidth()` عرض کامل را پر می‌کند
- `height(244.dp)` ارتفاع ثابت کارت
- `clickable()` کارت را قابل کلیک می‌کند
- `enabled = onClick != null` فقط اگر callback وجود داشته باشد
- `getCardColor(color)` رنگ کارت بر اساس نوع
- `Column` layout عمودی برای عنوان و محتوا
- `padding(12.dp)` فاصله داخلی کارت
- `AppTypography` استایل متن از design system

**`CustomButton.kt`** - دکمه سفارشی:
```kotlin
@Composable
fun CustomButton(
    text: String,
    type: ButtonType = ButtonType.Primary,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (type) {
                ButtonType.Primary -> PrimaryBase
                ButtonType.Secondary -> SecondaryBase
                ButtonType.Transparent -> Color.Transparent
            }
        )
    ) {
        Text(text = text)
    }
}
```

**توضیح کامل:**
- این کامپوننت دکمه سفارشی با انواع مختلف است
- `text: String` متن نمایشی دکمه
- `type: ButtonType` نوع دکمه (Primary, Secondary, Transparent)
- `onClick: () -> Unit` callback کلیک دکمه
- `enabled: Boolean` فعال/غیرفعال بودن دکمه
- `Button` Material Design 3 button component
- `ButtonDefaults.buttonColors()` تنظیم رنگ‌های دکمه
- `when (type)` انتخاب رنگ بر اساس نوع دکمه
- `ButtonType.Primary` رنگ اصلی اپلیکیشن
- `ButtonType.Secondary` رنگ ثانویه
- `ButtonType.Transparent` شفاف
- `Text` متن داخل دکمه
- قابلیت استفاده مجدد در کل اپلیکیشن

#### Theme (`ui/theme/`)

**`Theme.kt`** - تنظیمات تم:
```kotlin
@Composable
fun SimpleNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
```

**`Color.kt`** - پالت رنگی:
```kotlin
// Primary Colors
val PrimaryBase = Color(0xFF504EC3)
val PrimaryDark = Color(0xFF38368C)
val PrimaryLight = Color(0xFFEBEBFD)

// Secondary Colors
val SecondaryBase = Color(0xFFDEDC52)
val SecondaryLight = Color(0xFFF7F6D4)

// Neutral Colors
val NeutralBlack = Color(0xFF180E25)
val NeutralWhite = Color(0xFFFFFFFF)
```

### Configuration Files

#### `build.gradle.kts` (Project Level)
```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.google.dagger.hilt.android") version "2.54" apply false
}
```

#### `build.gradle.kts` (App Level)
```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sharif.simplenote"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sharif.simplenote"
        minSdk = 23
        targetSdk = 36
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    
    // Room
    implementation("androidx.room:room-runtime:2.8.0")
    ksp("androidx.room:room-compiler:2.8.0")
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.54")
    ksp("com.google.dagger:hilt-compiler:2.54")
    
    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.3.6")
    implementation("androidx.paging:paging-compose:3.3.6")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
}
```

#### `AndroidManifest.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".MyApplication"
        android:allowBackup="true"
        android:theme="@style/Theme.SimpleNote">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.SimpleNote">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

## نحوه اجرا

1. پروژه را در Android Studio باز کنید
2. SDK و build tools مورد نیاز را نصب کنید
3. پروژه را build کنید
4. روی یک دستگاه یا شبیه‌ساز اجرا کنید

## ویژگی‌های فنی

### معماری MVVM
- **Model**: مدل‌های داده و Repository ها
- **View**: Compose UI و Screens
- **ViewModel**: مدیریت state و business logic

### Dependency Injection
- استفاده از Hilt برای مدیریت وابستگی‌ها
- ماژول‌های جداگانه برای Database، API و App

### مدیریت State
- StateFlow برای مدیریت state در ViewModels
- Compose state برای UI state

### Networking
- Retrofit برای API calls
- JWT authentication
- Automatic token refresh

### Database
- Room database برای ذخیره محلی
- Paging برای نمایش کارآمد لیست‌ها
- Offline support

## نتیجه‌گیری

این پروژه نمونه‌ای از یک اپلیکیشن اندرویدی مدرن است که از بهترین شیوه‌های توسعه استفاده می‌کند. ساختار کد تمیز، معماری مناسب و استفاده از تکنولوژی‌های پیشرفته آن را به یک پایه قوی برای توسعه اپلیکیشن‌های پیچیده‌تر تبدیل می‌کند.