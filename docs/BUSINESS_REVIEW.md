# 📋 TÀI LIỆU REVIEW NGHIỆP VỤ HỆ THỐNG OPTICS MANAGEMENT

> **Mục đích:** Giải thích chi tiết nghiệp vụ hệ thống quản lý cửa hàng kính mắt, bao gồm luồng đặt hàng, vai trò người dùng, và các quy trình xử lý đơn hàng. Viết dễ hiểu để học sinh cấp 3 cũng nắm được.

---

## MỤC LỤC

1. [Tổng quan hệ thống](#1-tổng-quan-hệ-thống)
2. [Các vai trò (Role) trong hệ thống](#2-các-vai-trò-role-trong-hệ-thống)
3. [Sản phẩm trong hệ thống](#3-sản-phẩm-trong-hệ-thống)
4. [Luồng mua hàng thông thường (Gọng + Tròng kính)](#4-luồng-mua-hàng-thông-thường-gọng--tròng-kính)
5. [Luồng mua hàng Pre-Order (Đặt trước)](#5-luồng-mua-hàng-pre-order-đặt-trước)
6. [Hệ thống thanh toán](#6-hệ-thống-thanh-toán)
7. [Hệ thống hoàn tiền (Refund)](#7-hệ-thống-hoàn-tiền-refund)
8. [Combo khuyến mãi](#8-combo-khuyến-mãi)
9. [Tổng hợp trạng thái đơn hàng](#9-tổng-hợp-trạng-thái-đơn-hàng)
10. [Sơ đồ tổng quan Entity](#10-sơ-đồ-tổng-quan-entity)

---

## 1. TỔNG QUAN HỆ THỐNG

**Optics Management** là hệ thống quản lý cửa hàng kính mắt trực tuyến, cho phép:

- **Khách hàng** đặt mua gọng kính, tròng kính, phụ kiện
- **Nhân viên bán hàng** xác minh và xử lý đơn hàng
- **Nhân viên kỹ thuật** mài kính, lắp ráp theo đơn thuốc
- **Shipper** giao hàng
- **Quản lý** giám sát toàn bộ hoạt động

### Hình dung đơn giản

```
┌─────────────────────────────────────────────────────────────────┐
│                    HỆ THỐNG OPTICS MANAGEMENT                   │
│                                                                 │
│   👤 Khách hàng ──→ 🛒 Đặt hàng ──→ 💳 Thanh toán            │
│                          │                                      │
│                          ▼                                      │
│   👔 Sale Staff ──→ ✅ Xác minh đơn                            │
│                          │                                      │
│                          ▼                                      │
│   🔧 Operation  ──→ 🏭 Sản xuất/Lắp ráp kính                  │
│                          │                                      │
│                          ▼                                      │
│   🚚 Shipper    ──→ 📦 Giao hàng                               │
│                          │                                      │
│                          ▼                                      │
│   👤 Khách hàng ──→ ✅ Xác nhận nhận hàng ──→ HOÀN THÀNH      │
│                                                                 │
│   👑 Manager    ──→ 📊 Giám sát tất cả                         │
│   🛡️ Admin      ──→ ⚙️ Quản trị hệ thống                      │
└─────────────────────────────────────────────────────────────────┘
```

---

## 2. CÁC VAI TRÒ (ROLE) TRONG HỆ THỐNG

Hệ thống có **6 vai trò** chính. Mỗi người dùng được gán **một vai trò** xác định quyền hạn.

### 2.1 Bảng tổng hợp vai trò

| # | Vai trò | Tên trong code | Mô tả ngắn |
|---|---------|----------------|-------------|
| 1 | **Khách hàng** | `CUSTOMER` | Người mua kính, đặt hàng, thanh toán |
| 2 | **Nhân viên bán hàng** | `SALE` | Xác minh đơn hàng, kiểm tra đơn thuốc |
| 3 | **Nhân viên kỹ thuật** | `OPERATION` | Mài kính, lắp ráp, sản xuất |
| 4 | **Shipper** | `SHIPPER` | Nhận đơn, giao hàng |
| 5 | **Quản lý** | `MANAGER` | Giám sát tất cả, quản lý combo, hoàn tiền |
| 6 | **Quản trị viên** | `ADMIN` | Toàn quyền hệ thống |

### 2.2 Chi tiết từng vai trò

#### 👤 CUSTOMER (Khách hàng)

**Ai?** Bất kỳ ai đăng ký tài khoản trên hệ thống (mặc định khi đăng ký sẽ được gán role CUSTOMER).

**Làm được gì?**
- Xem danh sách sản phẩm (gọng kính, tròng kính, phụ kiện)
- Tạo đơn hàng mới (chọn sản phẩm + nhập đơn thuốc + chọn tròng kính)
- Thanh toán qua VNPay
- Xem lịch sử đơn hàng của mình
- Cập nhật đơn hàng (khi đang ở trạng thái PENDING hoặc ON_HOLD)
- Upload ảnh đơn thuốc (prescription)
- Hủy đơn hàng (trước khi đơn được xác nhận sản xuất)
- Xác nhận đã nhận hàng thành công
- Đánh giá & feedback sản phẩm
- Chat với AI chatbot để tư vấn

```
CUSTOMER có thể thao tác:

  📦 Tạo đơn hàng ─────────────┐
  📋 Xem đơn hàng của mình     │
  ✏️ Cập nhật đơn (PENDING)     ├──→ API: /orders/*
  ❌ Hủy đơn hàng               │
  ✅ Xác nhận nhận hàng ────────┘
  📷 Upload đơn thuốc
  💳 Thanh toán VNPay
  ⭐ Đánh giá sản phẩm
```

---

#### 👔 SALE (Nhân viên bán hàng)

**Ai?** Nhân viên bán hàng của cửa tiệm kính mắt.

**Làm được gì?**
- **Xem tất cả đơn hàng** trong hệ thống (lọc theo trạng thái)
- **Xác minh đơn hàng** (verify): Kiểm tra đơn thuốc có hợp lệ không, thông tin giao hàng có đúng không
  - ✅ Duyệt (approve) → đơn chuyển sang CONFIRMED hoặc PROCESSING
  - ❌ Từ chối (reject) → đơn bị ON_HOLD để khách cập nhật lại
- **Từ chối đơn hàng** hoàn toàn (reject) → đơn bị CANCELLED, hoàn kho
- **Hoàn tác xác minh** (revert) → quay lại AWAITING_VERIFICATION nếu nhầm
- **Kiểm tra xung đột giá** (price check): Kiểm tra giá combo có hợp lý không

```
SALE xử lý đơn hàng:

  Đơn vào (AWAITING_VERIFICATION)
         │
         ▼
  ┌─────────────────┐
  │  SALE kiểm tra  │
  │  - Đơn thuốc    │
  │  - Thông tin     │
  │  - Giá cả       │
  └────────┬────────┘
           │
     ┌─────┼─────┐
     ▼     ▼     ▼
   [OK]  [Chưa] [Sai]
     │   đúng    │
     │     │     │
     ▼     ▼     ▼
 CONFIRMED ON_HOLD CANCELLED
 /PROCESSING      (hoàn kho)
```

---

#### 🔧 OPERATION (Nhân viên kỹ thuật)

**Ai?** Thợ kính - người mài tròng, lắp ráp kính theo đơn thuốc.

**Làm được gì?**
- **Bắt đầu sản xuất** (start production): Chuyển đơn hàng sang trạng thái đang sản xuất
- **Cập nhật tiến độ từng item**: Đánh dấu từng sản phẩm trong đơn đã mài/lắp xong
- **Hoàn thành sản xuất** (finish production): Đánh dấu toàn bộ đơn đã sản xuất xong

```
OPERATION xử lý sản xuất:

  Đơn PROCESSING (đã xác minh)
         │
         ▼
  ┌──────────────────────┐
  │ OPERATION bắt đầu    │
  │ sản xuất (start)     │
  └──────────┬───────────┘
             │
    ┌────────┼────────┐
    ▼        ▼        ▼
  Item 1   Item 2   Item 3
  (mài     (mài     (có sẵn
   kính)    kính)    → PRODUCED)
    │        │
    ▼        ▼
  PRODUCED  PRODUCED
    │        │
    └────────┼────────┘
             ▼
  ┌──────────────────────┐
  │  Tất cả PRODUCED     │
  │  → Đơn = PRODUCED    │
  └──────────────────────┘
```

> **Lưu ý:** Chỉ các item cần xử lý (có tròng kính/đơn thuốc/pre-order) mới cần sản xuất. Item chỉ có gọng kính (IN_STOCK) thì tự động PRODUCED.

---

#### 🚚 SHIPPER

**Ai?** Nhân viên giao hàng.

**Làm được gì?**
- **Nhận đơn hàng** (accept): Chọn các đơn sẵn sàng giao để nhận ship
- **Bắt đầu giao** (start delivery): Đánh dấu đang trên đường giao
- **Xác nhận đã giao** (confirm delivered): Đánh dấu đã giao thành công
- **Xem danh sách đơn** mình đã nhận

```
SHIPPER luồng giao hàng:

  Đơn PRODUCED / READY_TO_SHIP
         │
         ▼
  ┌─────────────────────┐
  │ SHIPPER nhận đơn    │──→ SHIPPED
  │ (accept orders)     │
  └──────────┬──────────┘
             │
             ▼
  ┌─────────────────────┐
  │ Bắt đầu giao hàng  │──→ DELIVERING
  │ (start delivery)    │
  └──────────┬──────────┘
             │
             ▼
  ┌─────────────────────┐
  │ Giao thành công     │──→ DELIVERED → COMPLETED
  │ (confirm delivered) │
  └─────────────────────┘
```

---

#### 👑 MANAGER (Quản lý)

**Ai?** Quản lý cửa hàng kính mắt.

**Làm được gì?**
- **Xem tất cả đơn hàng** (giống SALE nhưng bao quát hơn)
- **Quản lý combo khuyến mãi**: Tạo, bật/tắt, cập nhật combo
- **Xử lý hoàn tiền** (refund): Duyệt và thực hiện hoàn tiền cho đơn bị hủy
- **Xem dashboard**: Thống kê doanh thu, đơn hàng, sản phẩm
- **Quản lý sản phẩm, tròng kính**: Thêm/sửa/xóa sản phẩm
- **Quản lý chính sách** (policy)
- Nhận thông báo khi có đơn hàng cần chú ý (ON_HOLD, đơn hủy đã thanh toán, v.v.)

---

#### 🛡️ ADMIN (Quản trị viên)

**Ai?** Người có quyền cao nhất trong hệ thống.

**Làm được gì?**
- **Tất cả những gì các role khác làm được**
- **Quản lý người dùng**: Tạo/cập nhật/xóa user, thay đổi role
- **Quản lý vai trò & quyền**: Tạo role, gán permission
- **Xóa đơn hàng** vĩnh viễn (chỉ ADMIN mới được)
- **Thay đổi trạng thái user**: Active/Inactive

---

### 2.3 Sơ đồ phân quyền tổng hợp

```
┌──────────────────────────────────────────────────────────────────┐
│                        PHÂN QUYỀN HỆ THỐNG                      │
├──────────────┬───────┬──────┬───────────┬─────────┬─────────────┤
│   Chức năng  │CUSTOMER│ SALE │ OPERATION │ SHIPPER │MANAGER/ADMIN│
├──────────────┼───────┼──────┼───────────┼─────────┼─────────────┤
│ Đặt hàng     │  ✅   │  ❌  │    ❌     │   ❌    │     ❌      │
│ Thanh toán    │  ✅   │  ❌  │    ❌     │   ❌    │     ❌      │
│ Hủy đơn      │  ✅   │  ❌  │    ❌     │   ❌    │     ❌      │
│ Xác nhận nhận│  ✅   │  ❌  │    ❌     │   ❌    │     ❌      │
│ Xem tất cả   │  ❌   │  ✅  │    ✅     │   ❌    │     ✅      │
│ Xác minh đơn │  ❌   │  ✅  │    ❌     │   ❌    │     ✅      │
│ Từ chối đơn  │  ❌   │  ✅  │    ❌     │   ❌    │     ✅      │
│ Sản xuất     │  ❌   │  ❌  │    ✅     │   ❌    │     ✅      │
│ Giao hàng    │  ❌   │  ❌  │    ❌     │   ✅    │     ✅      │
│ Quản lý user │  ❌   │  ❌  │    ❌     │   ❌    │   ADMIN ✅  │
│ Xóa đơn      │  ❌   │  ❌  │    ❌     │   ❌    │   ADMIN ✅  │
│ Quản lý combo│  ❌   │  ❌  │    ❌     │   ❌    │     ✅      │
│ Hoàn tiền    │  ❌   │  ❌  │    ❌     │   ❌    │     ✅      │
│ Dashboard    │  ❌   │  ❌  │    ❌     │   ❌    │     ✅      │
└──────────────┴───────┴──────┴───────────┴─────────┴─────────────┘
```

---

## 3. SẢN PHẨM TRONG HỆ THỐNG

### 3.1 Cấu trúc sản phẩm

Hệ thống quản lý sản phẩm theo cấu trúc phân cấp:

```
┌──────────────────────────────────────────────────────────┐
│                    PRODUCT (Sản phẩm)                    │
│  - name: "Gọng kính Rayban Aviator"                     │
│  - brand: "Rayban"                                       │
│  - category: FRAME / LENS / ACCESSORY                    │
│  - frameType, shape, gender, material...                 │
│                                                          │
│  ┌────────────────────────────────────────────────────┐  │
│  │  PRODUCT VARIANT (Phiên bản sản phẩm / SKU)       │  │
│  │  - colorName: "Đen bóng"                           │  │
│  │  - sizeLabel: "M"                                  │  │
│  │  - price: 1.500.000 VNĐ                           │  │
│  │  - orderItemType: IN_STOCK hoặc PRE_ORDER          │  │
│  │                                                    │  │
│  │  ┌──────────────────────────────────────────────┐  │  │
│  │  │  INVENTORY (Tồn kho)                         │  │  │
│  │  │  - quantity: 50 (số lượng thực tế)           │  │  │
│  │  │  - reservedQuantity: 5 (đã đặt, chờ giao)   │  │  │
│  │  └──────────────────────────────────────────────┘  │  │
│  └────────────────────────────────────────────────────┘  │
│                                                          │
│  ┌────────────────────────────────────────────────────┐  │
│  │  PRODUCT IMAGE (Ảnh sản phẩm)                      │  │
│  └────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────┐
│                      LENS (Tròng kính)                   │
│  - name: "Tròng cận chống ánh sáng xanh"                │
│  - material: "CR-39"                                     │
│  - price: 500.000 VNĐ                                   │
│  - description: "Chống UV, chống phản quang"             │
└──────────────────────────────────────────────────────────┘
```

### 3.2 Hai loại sản phẩm quan trọng

| Loại | `OrderItemType` | Ý nghĩa | Ví dụ |
|------|-----------------|----------|-------|
| **Có sẵn** | `IN_STOCK` | Có hàng trong kho, giao ngay | Gọng kính có sẵn trong cửa hàng |
| **Đặt trước** | `PRE_ORDER` | Chưa có hàng, phải đặt sản xuất | Gọng kính limited edition, hàng nhập |

> **Phân biệt:** Loại sản phẩm được xác định ở cấp `ProductVariant.orderItemType`, nghĩa là cùng một mẫu gọng kính, phiên bản màu đen có thể có sẵn (IN_STOCK), nhưng phiên bản màu vàng gold phải đặt trước (PRE_ORDER).

---

## 4. LUỒNG MUA HÀNG THÔNG THƯỜNG (Gọng + Tròng kính)

Đây là luồng phổ biến nhất: Khách hàng mua gọng kính có sẵn (IN_STOCK) kèm tròng kính cắt theo đơn thuốc.

### 4.1 Sơ đồ tổng quan

```
╔══════════════════════════════════════════════════════════════════════╗
║           LUỒNG MUA GỌNG KÍNH + TRÒNG KÍNH (IN_STOCK)             ║
╠══════════════════════════════════════════════════════════════════════╣
║                                                                    ║
║  BƯỚC 1: KHÁCH HÀNG ĐẶT HÀNG                                     ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 👤 Customer chọn:                            │                  ║
║  │  • Gọng kính (ProductVariant - IN_STOCK)     │                  ║
║  │  • Tròng kính (Lens)                         │                  ║
║  │  • Nhập đơn thuốc (Prescription)             │                  ║
║  │  • Upload ảnh đơn thuốc (tuỳ chọn)          │                  ║
║  │  • Nhập thông tin giao hàng                  │                  ║
║  │  • Nhập thông tin ngân hàng (để hoàn tiền)   │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                         ▼                                          ║
║           Đơn hàng được tạo: PENDING                               ║
║                         │                                          ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 2: THANH TOÁN                                                ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 💳 Customer thanh toán 100% qua VNPay        │                  ║
║  │    (vì là IN_STOCK → trả full)               │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                    Thanh toán OK?                                   ║
║                    /          \                                     ║
║                  ✅            ❌                                    ║
║                  │            │                                     ║
║                  ▼            ▼                                     ║
║     AWAITING_VERIFICATION   PENDING                                ║
║     (chờ staff xác minh)   (thử lại)                              ║
║                  │                                                 ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 3: SALE XÁC MINH                                            ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 👔 Sale kiểm tra:                            │                  ║
║  │  • Đơn thuốc có hợp lệ không?               │                  ║
║  │  • Thông tin giao hàng đúng không?           │                  ║
║  │  • Số liệu mắt có logic không?              │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                Kết quả xác minh?                                   ║
║              /          |          \                                ║
║           ✅ OK    ⚠️ Chưa đúng   ❌ Sai                           ║
║            │          │              │                              ║
║            ▼          ▼              ▼                              ║
║       PROCESSING   ON_HOLD      CANCELLED                          ║
║       (có tròng    (khách       (từ chối,                          ║
║        → cần SX)    sửa lại)    hoàn kho)                         ║
║            │          │                                            ║
║            │          └─→ Khách sửa → quay lại xác minh           ║
║            │                                                       ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 4: SẢN XUẤT                                                 ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 🔧 Operation (thợ kính):                     │                  ║
║  │  • Mài tròng kính theo đơn thuốc             │                  ║
║  │  • Lắp tròng vào gọng                        │                  ║
║  │  • Kiểm tra chất lượng                       │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║               Cập nhật từng item:                                  ║
║            IN_PRODUCTION → PRODUCED                                ║
║                         │                                          ║
║              Tất cả item xong?                                     ║
║                    ✅                                               ║
║                    ▼                                               ║
║               PRODUCED                                             ║
║                                                                    ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 5: GIAO HÀNG                                                ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 🚚 Shipper:                                  │                  ║
║  │  1. Nhận đơn (accept) → SHIPPED              │                  ║
║  │  2. Bắt đầu giao → DELIVERING                │                  ║
║  │  3. Giao xong → DELIVERED → COMPLETED        │                  ║
║  └──────────────────────────────────────────────┘                  ║
║                                                                    ║
╚══════════════════════════════════════════════════════════════════════╝
```

### 4.2 Giải thích từng bước chi tiết

#### Bước 1: Tạo đơn hàng

Khi khách tạo đơn, hệ thống sẽ:

1. **Kiểm tra sản phẩm**: ProductVariant phải tồn tại và đang ACTIVE
2. **Xác định loại item**: Dựa vào `ProductVariant.orderItemType` (IN_STOCK hoặc PRE_ORDER)
3. **Kiểm tra tồn kho**: Nếu IN_STOCK → kiểm tra inventory, trừ kho ngay (reserve)
4. **Tính giá từng item**: `(giá gọng + giá tròng) × số lượng`
5. **Kiểm tra đơn thuốc**: Nếu có tròng kính (lens) → **bắt buộc phải có đơn thuốc**
6. **Áp dụng combo** (nếu có): Tính giảm giá
7. **Tính số tiền cần thanh toán**:
   - IN_STOCK: Trả **100%** giá gọng + **100%** phí tròng
   - PRE_ORDER: Trả **50%** giá gọng + **100%** phí tròng (đặt cọc)

```
Ví dụ tính giá cho 1 cặp kính IN_STOCK:

  Gọng kính Rayban:     1.500.000 VNĐ  × 1 = 1.500.000
  Tròng cận chống xanh:   500.000 VNĐ  × 1 =   500.000
                                         ─────────────────
  Tổng 1 item:                           = 2.000.000 VNĐ
  
  IN_STOCK → thanh toán 100% gọng + 100% tròng
  Số tiền phải trả: 1.500.000 × 100% + 500.000 = 2.000.000 VNĐ
```

#### Bước 2: Thanh toán

- Phương thức: **VNPay** (bắt buộc cho đơn có tròng kính hoặc pre-order)
- Sau khi thanh toán thành công:
  - Nếu đơn có item đặc biệt (tròng kính/đơn thuốc/pre-order) → `AWAITING_VERIFICATION`
  - Nếu đơn chỉ có gọng kính IN_STOCK, không có tròng → `PREPARING` (bỏ qua xác minh)

#### Bước 3: Sale xác minh

Sale staff kiểm tra đơn thuốc (prescription) gồm các thông số:
- **OD** (mắt phải): Sphere, Cylinder, Axis, Add, PD
- **OS** (mắt trái): Sphere, Cylinder, Axis, Add, PD
- Ảnh đơn thuốc (nếu có upload)

Kết quả xác minh:
- **Approve** → đơn chuyển sang `PROCESSING` (vì có tròng kính cần mài)
- **Reject (tạm)** → `ON_HOLD` (khách sửa lại đơn thuốc/thông tin)
- **Reject (vĩnh viễn)** → `CANCELLED` (hoàn kho, tạo yêu cầu hoàn tiền nếu đã thanh toán)

#### Bước 4: Sản xuất

Operation staff thực hiện:
1. **Start production**: Đặt các item cần xử lý thành `IN_PRODUCTION`
   - Item nào cần xử lý? → Có tròng kính, hoặc có đơn thuốc, hoặc là PRE_ORDER
   - Item chỉ là gọng IN_STOCK (không kèm tròng) → tự động `PRODUCED`
2. **Cập nhật từng item**: Mài xong item nào thì đánh dấu `PRODUCED`
3. **Finish production**: Khi tất cả item đều `PRODUCED` → đơn hàng = `PRODUCED`

#### Bước 5: Giao hàng

1. Shipper xem danh sách đơn `PRODUCED` / `READY_TO_SHIP`
2. **Accept**: Nhận đơn → `SHIPPED` (ghi nhận shipperId, thời gian nhận)
3. **Start delivery**: Bắt đầu giao → `DELIVERING`
4. **Confirm delivered**: Giao xong → `DELIVERED` → tự động chuyển `COMPLETED`

---

## 5. LUỒNG MUA HÀNG PRE-ORDER (ĐẶT TRƯỚC)

Luồng Pre-Order phức tạp hơn vì liên quan đến việc **thanh toán 2 lần** (đặt cọc 50% rồi trả phần còn lại).

### 5.1 Sơ đồ tổng quan

```
╔══════════════════════════════════════════════════════════════════════╗
║              LUỒNG MUA HÀNG PRE-ORDER (ĐẶT TRƯỚC)                 ║
╠══════════════════════════════════════════════════════════════════════╣
║                                                                    ║
║  BƯỚC 1: KHÁCH HÀNG ĐẶT HÀNG                                     ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 👤 Customer chọn sản phẩm PRE_ORDER          │                  ║
║  │  (+ có thể kèm tròng kính + đơn thuốc)      │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                         ▼                                          ║
║          Đơn hàng: PENDING                                         ║
║          PreOrder: DEPOSIT_PENDING                                 ║
║                         │                                          ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 2: THANH TOÁN LẦN 1 (ĐẶT CỌC 50%)                         ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 💳 Customer trả 50% giá gọng + 100% tròng   │                  ║
║  │    qua VNPay (DEPOSIT)                        │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                    Thanh toán OK                                   ║
║                         │                                          ║
║                         ▼                                          ║
║          Đơn hàng: AWAITING_VERIFICATION                           ║
║          PreOrder: DEPOSIT_PAID                                    ║
║                         │                                          ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 3: SALE XÁC MINH (giống luồng thường)                       ║
║                         │                                          ║
║                    ✅ Approve                                       ║
║                         │                                          ║
║                         ▼                                          ║
║          Đơn hàng: PROCESSING                                      ║
║                         │                                          ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 4: SẢN XUẤT                                                 ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 🔧 Operation mài kính, lắp ráp               │                  ║
║  │    IN_PRODUCTION → PRODUCED                   │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                         ▼                                          ║
║          Đơn hàng: PRODUCED                                        ║
║                         │                                          ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 5: THANH TOÁN LẦN 2 (TRẢ 50% CÒN LẠI)                     ║
║  ┌──────────────────────────────────────────────┐                  ║
║  │ 💳 Customer trả 50% còn lại qua VNPay        │                  ║
║  │    (REMAINING)                                │                  ║
║  └──────────────────────┬───────────────────────┘                  ║
║                         │                                          ║
║                    Thanh toán OK                                   ║
║                         │                                          ║
║                         ▼                                          ║
║          Đơn hàng: PREPARING                                       ║
║          PreOrder: REMAINING_PAID                                  ║
║                         │                                          ║
║  ────────────────────────────────────────────────────────          ║
║                                                                    ║
║  BƯỚC 6: GIAO HÀNG (giống luồng thường)                           ║
║          SHIPPED → DELIVERING → DELIVERED → COMPLETED              ║
║                                                                    ║
╚══════════════════════════════════════════════════════════════════════╝
```

### 5.2 So sánh IN_STOCK vs PRE_ORDER

```
┌─────────────────────┬──────────────────────┬──────────────────────┐
│                     │      IN_STOCK         │     PRE_ORDER        │
├─────────────────────┼──────────────────────┼──────────────────────┤
│ Hàng có sẵn?        │ ✅ Có                 │ ❌ Chưa có           │
│ Kiểm tra tồn kho?  │ ✅ Có (trừ kho ngay)  │ ❌ Không             │
│ Thanh toán lần 1    │ 100% tổng giá         │ 50% gọng + 100% tròng│
│ Cần thanh toán lần 2│ ❌ Không              │ ✅ 50% gọng còn lại  │
│ PreOrderStatus      │ Không có              │ DEPOSIT_PENDING →    │
│                     │                      │ DEPOSIT_PAID →       │
│                     │                      │ REMAINING_PAID       │
│ Thời gian giao      │ Nhanh hơn            │ Lâu hơn (chờ SX)    │
└─────────────────────┴──────────────────────┴──────────────────────┘
```

### 5.3 Cách tính tiền Pre-Order (Ví dụ)

```
Ví dụ: Mua 1 gọng PRE_ORDER + 1 tròng kính

  Gọng kính Limited:   2.000.000 VNĐ  (PRE_ORDER)
  Tròng chống xanh:      500.000 VNĐ

  Tổng đơn hàng:       2.500.000 VNĐ

  ╔═══════════════════════════════════════════════════╗
  ║  THANH TOÁN LẦN 1 (Đặt cọc - DEPOSIT):          ║
  ║                                                   ║
  ║  50% giá gọng:  2.000.000 × 50% = 1.000.000     ║
  ║  100% phí tròng:                  =   500.000     ║
  ║                                   ─────────────   ║
  ║  Tổng trả lần 1:                 = 1.500.000 VNĐ ║
  ╚═══════════════════════════════════════════════════╝

  ... (chờ sản xuất xong) ...

  ╔═══════════════════════════════════════════════════╗
  ║  THANH TOÁN LẦN 2 (Trả còn lại - REMAINING):    ║
  ║                                                   ║
  ║  50% gọng còn lại: 2.500.000 - 1.500.000         ║
  ║                                   = 1.000.000 VNĐ║
  ╚═══════════════════════════════════════════════════╝
```

### 5.4 Trạng thái Pre-Order (PreOrderStatus)

```
DEPOSIT_PENDING ──→ DEPOSIT_PAID ──→ PRODUCING ──→ PRODUCED
     │                   │                              │
     │          (đã cọc 50%)                   (sản xuất xong)
     │                                                  │
     │                                                  ▼
     │                                          REMAINING_PENDING
     │                                                  │
     │                                          (chờ trả 50% còn lại)
     │                                                  │
     │                                                  ▼
     │                                          REMAINING_PAID
     │                                                  │
     │                                          (đã trả đủ 100%)
     │                                                  │
     │                                                  ▼
     └──────────────────────────────────────→ READY_FOR_DELIVERY
                                                        │
                                                        ▼
                                                   COMPLETED
```

---

## 6. HỆ THỐNG THANH TOÁN

### 6.1 Phương thức thanh toán

| Phương thức | Code | Mô tả |
|-------------|------|--------|
| **VNPay** | `VNPAY` | Thanh toán trực tuyến qua cổng VNPay |
| **COD** | `COD` | Thanh toán khi nhận hàng (chỉ cho đơn IN_STOCK thuần, không có tròng) |

### 6.2 Mục đích thanh toán (PaymentPurpose)

```
┌──────────────┬────────────────────────────────────────────────┐
│ Purpose      │ Giải thích                                     │
├──────────────┼────────────────────────────────────────────────┤
│ FULL         │ Thanh toán 100% (đơn IN_STOCK thuần)           │
│ DEPOSIT      │ Đặt cọc 50% (đơn PRE_ORDER - lần 1)           │
│ REMAINING    │ Trả 50% còn lại (đơn PRE_ORDER - lần 2)       │
│ REFUND       │ Hoàn tiền (khi đơn bị hủy/hoàn)               │
└──────────────┴────────────────────────────────────────────────┘
```

### 6.3 Luồng thanh toán VNPay

```
👤 Customer               Hệ thống              VNPay
     │                       │                     │
     │  Bấm "Thanh toán"     │                     │
     │──────────────────────→│                     │
     │                       │  Tạo Payment URL    │
     │                       │────────────────────→│
     │                       │  Trả về URL VNPay   │
     │                       │←────────────────────│
     │  Redirect đến VNPay   │                     │
     │←──────────────────────│                     │
     │                       │                     │
     │  Nhập thông tin thẻ   │                     │
     │──────────────────────────────────────────→  │
     │                       │                     │
     │                       │   Callback kết quả  │
     │                       │←────────────────────│
     │                       │                     │
     │                       │  Cập nhật Payment:  │
     │                       │  PAID hoặc FAILED   │
     │                       │                     │
     │                       │  Cập nhật Order:    │
     │                       │  AWAITING_VERIFICATION│
     │                       │  hoặc PREPARING     │
     │  Hiển thị kết quả     │                     │
     │←──────────────────────│                     │
```

---

## 7. HỆ THỐNG HOÀN TIỀN (REFUND)

### 7.1 Khi nào cần hoàn tiền?

1. **Khách hàng tự hủy đơn** (đã thanh toán) → hoàn **95%**
2. **Nhà sản xuất hủy** (variant bị inactive, không sản xuất được) → hoàn **100%** tiền cọc

### 7.2 Luồng hoàn tiền

```
╔═══════════════════════════════════════════════════════════════╗
║                    LUỒNG HOÀN TIỀN                           ║
╠═══════════════════════════════════════════════════════════════╣
║                                                              ║
║  1. Đơn hàng bị CANCELLED (đã thanh toán)                    ║
║     hoặc variant bị INACTIVE (pre-order)                     ║
║                  │                                           ║
║                  ▼                                           ║
║  2. Manager tạo yêu cầu hoàn tiền                           ║
║     ┌────────────────────────────────────┐                   ║
║     │ Refund = READY_FOR_REFUND          │                   ║
║     │ - Khách tự hủy: hoàn 95%          │                   ║
║     │ - NSX hủy: hoàn 100% tiền cọc    │                   ║
║     └───────────────┬────────────────────┘                   ║
║                     │                                        ║
║                     ▼                                        ║
║  3. Manager thực hiện hoàn tiền qua VNPay                    ║
║     ┌────────────────────────────────────┐                   ║
║     │ Refund = PROCESSING                │                   ║
║     │ Tạo Payment (REFUND)               │                   ║
║     └───────────────┬────────────────────┘                   ║
║                     │                                        ║
║                VNPay xử lý                                   ║
║                /          \                                  ║
║             ✅ OK       ❌ FAIL                               ║
║              │            │                                  ║
║              ▼            ▼                                  ║
║         COMPLETED      FAILED                                ║
║         (Order →       (thử lại)                             ║
║          REFUNDED)                                           ║
║                                                              ║
╚═══════════════════════════════════════════════════════════════╝
```

### 7.3 Ví dụ tính hoàn tiền

```
Ví dụ 1: Khách tự hủy đơn (đã trả 2.000.000)

  Số tiền đã trả:           2.000.000 VNĐ
  Tỷ lệ hoàn:              95%
  Số tiền hoàn:             2.000.000 × 95% = 1.900.000 VNĐ
  Phí phạt hủy (5%):       2.000.000 × 5%  =   100.000 VNĐ

Ví dụ 2: NSX hủy đơn pre-order (đã cọc 1.500.000)

  Số tiền đặt cọc:         1.500.000 VNĐ
  Tỷ lệ hoàn:              100%
  Số tiền hoàn:             1.500.000 VNĐ (hoàn toàn bộ)
```

---

## 8. COMBO KHUYẾN MÃI

### 8.1 Combo là gì?

Combo là chương trình khuyến mãi: **Mua đủ các sản phẩm chỉ định → được giảm giá**.

### 8.2 Cấu trúc Combo

```
┌──────────────────────────────────────────────────────────────┐
│                        COMBO                                 │
│  name: "Combo học sinh mùa hè"                              │
│  discountType: PERCENT (%) hoặc FIXED_AMOUNT (VNĐ)          │
│  discountValue: 15 (giảm 15%)                                │
│  startTime: 01/06/2026                                       │
│  endTime: 31/08/2026                                         │
│  status: SCHEDULED → ACTIVE → EXPIRED                        │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  COMBO ITEM 1:                                         │  │
│  │  - ProductVariant: Gọng kính ABC màu đen               │  │
│  │  - requiredQuantity: 1                                  │  │
│  ├────────────────────────────────────────────────────────┤  │
│  │  COMBO ITEM 2:                                         │  │
│  │  - ProductVariant: Tròng chống xanh XYZ                │  │
│  │  - requiredQuantity: 2                                  │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

### 8.3 Loại giảm giá

| Loại | Ví dụ | Cách tính |
|------|-------|-----------|
| **PERCENT** | Giảm 15% | Tổng giá combo items × 15% |
| **FIXED_AMOUNT** | Giảm 200.000đ | Trừ thẳng 200.000đ khỏi tổng |

### 8.4 Trạng thái Combo

```
SCHEDULED ──→ ACTIVE ──→ EXPIRED
    │             │
    │             │ (Manager tắt thủ công)
    │             ▼
    │          INACTIVE
    │             │
    │             │ (Manager bật lại)
    │             ▼
    └──────→ ACTIVE (nếu còn trong thời gian)
```

### 8.5 Điều kiện áp dụng combo

1. Combo phải đang **ACTIVE**
2. Thời gian hiện tại nằm trong **startTime ~ endTime**
3. Đơn hàng phải chứa **đủ** các sản phẩm (ProductVariant) với **đủ số lượng** yêu cầu
4. Tồn kho đủ cho combo items

---

## 9. TỔNG HỢP TRẠNG THÁI ĐƠN HÀNG

### 9.1 Bảng trạng thái (OrderStatus)

| # | Trạng thái | Ý nghĩa | Ai xử lý? |
|---|-----------|---------|-----------|
| 1 | `PENDING` | Mới tạo, chưa thanh toán | Customer |
| 2 | `AWAITING_VERIFICATION` | Đã thanh toán, chờ staff kiểm tra | Sale |
| 3 | `ON_HOLD` | Bị tạm dừng (đơn thuốc chưa hợp lệ) | Customer sửa |
| 4 | `CONFIRMED` | Đã xác nhận (không cần sản xuất) | Tự động |
| 5 | `PREPARING` | Đang chuẩn bị hàng (IN_STOCK sau thanh toán) | Tự động |
| 6 | `PROCESSING` | Có item đang sản xuất | Operation |
| 7 | `PRODUCED` | Tất cả item đã xong | Operation |
| 8 | `READY_TO_SHIP` | Sẵn sàng giao | Chờ Shipper |
| 9 | `SHIPPED` | Shipper đã nhận đơn | Shipper |
| 10 | `DELIVERING` | Đang giao hàng | Shipper |
| 11 | `DELIVERED` | Đã giao thành công | Shipper |
| 12 | `COMPLETED` | Hoàn thành | Tự động/Customer |
| 13 | `CANCELLED` | Đã hủy | Customer/Sale |
| 14 | `REFUNDED` | Đã hoàn tiền | Manager |

### 9.2 Sơ đồ chuyển trạng thái đầy đủ

```
                              ┌──────────┐
                              │ PENDING  │ (Khách tạo đơn)
                              └────┬─────┘
                                   │
                          💳 Thanh toán VNPay
                                   │
                          ┌────────┴────────┐
                          │                 │
               (Có tròng/đơn thuốc/    (Chỉ gọng IN_STOCK
                pre-order)              không tròng)
                          │                 │
                          ▼                 ▼
              ┌───────────────────┐  ┌───────────┐
              │AWAITING_VERIFICATION│  │ PREPARING │
              └─────────┬─────────┘  └─────┬─────┘
                        │                  │
                  Sale xác minh            │
                 /      |      \           │
                ▼       ▼       ▼          │
           ┌────────┐ ┌──────┐ ┌─────────┐│
           │ON_HOLD │ │CANCEL│ │CONFIRMED││
           │(sửa)   │ │LED   │ │/PROCESS.││
           └───┬────┘ └──────┘ └────┬────┘│
               │                    │     │
               └──→ (sửa xong,     │     │
                   quay lại         │     │
                   xác minh)        │     │
                                    │     │
                          ┌─────────┴─────┘
                          │
                          ▼
                   ┌────────────┐
                   │ PROCESSING │ (Operation sản xuất)
                   └──────┬─────┘
                          │
                   Tất cả item xong
                          │
                          ▼
                   ┌────────────┐
           ┌───────│  PRODUCED  │
           │       └──────┬─────┘
           │              │
    (Pre-order:      (Sẵn sàng giao)
     trả 50%              │
     còn lại)             │
           │              │
           ▼              ▼
    ┌────────────┐ ┌──────────────┐
    │ PREPARING  │ │READY_TO_SHIP │
    └──────┬─────┘ └──────┬───────┘
           │              │
           └──────┬───────┘
                  │
                  ▼
           ┌────────────┐
           │  SHIPPED   │ (Shipper nhận)
           └──────┬─────┘
                  │
                  ▼
           ┌────────────┐
           │ DELIVERING │ (Đang giao)
           └──────┬─────┘
                  │
                  ▼
           ┌────────────┐
           │ DELIVERED  │ (Đã giao)
           └──────┬─────┘
                  │
                  ▼
           ┌────────────┐
           │ COMPLETED  │ ✅ HOÀN THÀNH
           └────────────┘

     Bất cứ lúc nào (trước PROCESSING):

           ┌────────────┐         ┌────────────┐
           │ CANCELLED  │ ──────→ │  REFUNDED  │
           └────────────┘  (nếu   └────────────┘
                         đã trả tiền
                         → hoàn tiền)
```

---

## 10. SƠ ĐỒ TỔNG QUAN ENTITY

### 10.1 Quan hệ giữa các bảng (Entity Relationship)

```
┌─────────────┐       ┌──────────────┐
│    User     │       │    Role      │
│  - id       │◄─────►│  - name      │  (Many-to-Many)
│  - username │       │  - description│
│  - password │       └──────┬───────┘
│  - email    │              │
│  - phone    │       ┌──────┴───────┐
│  - status   │       │  Permission  │  (Many-to-Many)
└──────┬──────┘       │  - name      │
       │              └──────────────┘
       │ (1 User → N Orders)
       │
       ▼
┌──────────────┐      ┌──────────────┐
│   Orders     │─────►│    Combo     │  (Many-to-1)
│  - id        │      │  - name      │
│  - status    │      │  - discount  │
│  - total     │      │  - startTime │
│  - deposit   │      │  - endTime   │
│  - remaining │      └──────┬───────┘
│  - address   │             │
│  - phone     │      ┌──────┴───────┐
│  - bankInfo  │      │  ComboItem   │
└──────┬───────┘      │  - variant   │
       │              │  - quantity  │
       │              └──────────────┘
       │ (1 Order → N OrderItems)
       │
       ▼
┌──────────────┐      ┌──────────────────┐
│  OrderItem   │─────►│  ProductVariant   │
│  - id        │      │  - colorName     │
│  - quantity  │      │  - price         │
│  - unitPrice │      │  - orderItemType │
│  - lensPrice │      │  - status        │
│  - type      │      └────────┬─────────┘
│  - status    │               │
└──┬───┬───────┘        ┌──────┴───────┐
   │   │                │   Product    │
   │   │                │  - name      │
   │   │                │  - brand     │
   │   │                │  - category  │
   │   │                └──────────────┘
   │   │
   │   └─────────────►┌──────────────┐
   │                   │ Prescription │
   │                   │  - odSphere  │
   │                   │  - odCylinder│
   │                   │  - osSphere  │
   │                   │  - imageUrl  │
   │                   └──────────────┘
   │
   └──────────────────►┌──────────────┐
                       │  Inventory   │
                       │  - quantity  │
                       │  - reserved  │
                       └──────────────┘

┌──────────────┐      ┌──────────────┐
│   Payment    │─────►│  Transaction │
│  - amount    │      │  - type      │
│  - method    │      │  - amount    │
│  - purpose   │      │  - reference │
│  - status    │      └──────────────┘
│  - order     │
└──────────────┘

┌──────────────┐
│   Refund     │
│  - order     │
│  - amount    │
│  - percentage│
│  - bankInfo  │
│  - status    │
└──────────────┘

┌──────────────┐
│  Feedback    │
│  - order     │
│  - product   │
│  - customer  │
│  - rating    │
│  - comment   │
│  - images    │
└──────────────┘

┌──────────────┐
│ Notification │ (Thông báo tự động cho các role)
└──────────────┘
```

---

## PHỤ LỤC: BẢNG THUẬT NGỮ

| Thuật ngữ | Tiếng Việt | Giải thích |
|-----------|-----------|------------|
| **Order** | Đơn hàng | Một lần mua hàng của khách |
| **OrderItem** | Mục trong đơn | Từng sản phẩm trong đơn hàng |
| **Product** | Sản phẩm | Gọng kính, tròng kính, phụ kiện |
| **ProductVariant** | Phiên bản SP | Cùng mẫu nhưng khác màu/size |
| **Lens** | Tròng kính | Mắt kính (cần mài theo đơn thuốc) |
| **Prescription** | Đơn thuốc | Số liệu mắt (cận, viễn, loạn) |
| **Inventory** | Tồn kho | Số lượng sản phẩm có sẵn |
| **IN_STOCK** | Có sẵn | Hàng có trong kho, giao ngay |
| **PRE_ORDER** | Đặt trước | Hàng chưa có, phải chờ sản xuất/nhập |
| **DEPOSIT** | Đặt cọc | Trả trước 50% (pre-order) |
| **REMAINING** | Còn lại | Trả 50% sau khi hàng về |
| **Combo** | Khuyến mãi | Mua combo sản phẩm được giảm giá |
| **Refund** | Hoàn tiền | Trả lại tiền khi hủy đơn |
| **VNPay** | Cổng TT | Cổng thanh toán trực tuyến VN |
| **OD** | Mắt phải | Oculus Dexter (viết tắt y khoa) |
| **OS** | Mắt trái | Oculus Sinister (viết tắt y khoa) |
| **Sphere** | Cầu | Độ cận (-) hoặc viễn (+) |
| **Cylinder** | Trụ | Độ loạn thị |
| **Axis** | Trục | Hướng loạn thị (0-180°) |
| **PD** | Khoảng cách đồng tử | Pupillary Distance |

---

> **Tài liệu này được tạo tự động từ mã nguồn hệ thống Optics Management.**
> **Phiên bản:** v1.0 - Ngày tạo: 14/04/2026
