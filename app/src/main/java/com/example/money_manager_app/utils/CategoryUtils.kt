package com.example.money_manager_app.utils

import com.example.money_manager_app.R
import com.example.money_manager_app.data.model.LanguageModel
import com.example.money_manager_app.data.model.entity.Category
import com.example.money_manager_app.data.model.entity.enums.CategoryType

object CategoryUtils {
    val listCategory: List<Category> = listOf(
        Category(1, "Award", 1, R.drawable.income_1, R.color.color_1, CategoryType.INCOME),
        Category(2, "Allowance", 1, R.drawable.income_2, R.color.color_2, CategoryType.INCOME),
        Category(3, "Dividend", 1, R.drawable.income_3, R.color.color_3, CategoryType.INCOME),
        Category(4, "Bonus", 1, R.drawable.income_4, R.color.color_4, CategoryType.INCOME),
        Category(5, "Investment", 1, R.drawable.income_5, R.color.color_5, CategoryType.INCOME),
        Category(6, "Lottery", 1, R.drawable.income_6, R.color.color_6, CategoryType.INCOME),
        Category(7, "Salary", 1, R.drawable.income_7, R.color.color_7, CategoryType.INCOME),
        Category(8, "Tips", 1, R.drawable.income_8, R.color.color_8, CategoryType.INCOME),
        Category(9, "Others", 1, R.drawable.income_9, R.color.color_9, CategoryType.INCOME),
        Category(10, "Bill", 1, R.drawable.expense_1, R.color.color_10, CategoryType.EXPENSE),
        Category(11, "Clothing", 1, R.drawable.expense_2, R.color.color_11, CategoryType.EXPENSE),
        Category(12, "Education", 1, R.drawable.expense_3, R.color.color_12, CategoryType.EXPENSE),
        Category(13, "Entertainment", 1, R.drawable.expense_4, R.color.color_13, CategoryType.EXPENSE),
        Category(14, "Fitness", 1, R.drawable.expense_5, R.color.color_14, CategoryType.EXPENSE),
        Category(15, "Food", 1, R.drawable.expense_6, R.color.color_15, CategoryType.EXPENSE),
        Category(16, "Furniture", 1, R.drawable.expense_7, R.color.color_16, CategoryType.EXPENSE),
        Category(17, "Gift", 1, R.drawable.expense_8, R.color.color_17, CategoryType.EXPENSE),
        Category(18, "Health", 1, R.drawable.expense_9, R.color.color_18, CategoryType.EXPENSE),
        Category(19, "Pet", 1, R.drawable.expense_10, R.color.color_19, CategoryType.EXPENSE),
        Category(20, "Shopping", 1, R.drawable.expense_11, R.color.color_20, CategoryType.EXPENSE),
        Category(21, "Transportation", 1, R.drawable.expense_12, R.color.color_21, CategoryType.EXPENSE),
        Category(22, "Travel", 1, R.drawable.expense_13, R.color.color_22, CategoryType.EXPENSE),
        Category(23, "Other", 1, R.drawable.expense_14, R.color.color_23, CategoryType.EXPENSE),
        Category(24, "Transfer", 1, R.drawable.transfer_1, R.color.color_24, CategoryType.TRANSFER),
        Category(25, "REPAYMENT", 1, R.drawable.wallet_11, R.color.color_25, CategoryType.REPAYMENT),
        Category(26, "DEBT INCREASE", 1, R.drawable.wallet_11, R.color.color_26, CategoryType.DEBT_INCREASE),
        Category(27, "LOAN INTEREST", 1, R.drawable.wallet_6, R.color.color_27, CategoryType.LOAN_INTEREST),
        Category(28, "DEBT COLLECTION", 1, R.drawable.wallet_11, R.color.color_28, CategoryType.DEBT_COLLECTION),
        Category(29, "LOAN INCREASE", 1, R.drawable.wallet_11, R.color.color_29, CategoryType.LOAN_INCREASE),
        Category(30, "Deposit", 1, R.drawable.deposit, R.color.color_30, CategoryType.DEPOSIT),
        Category(31, "Withdraw", 1, R.drawable.withdraw, R.color.color_31, CategoryType.WITHDRAW),
        Category(32, "PAYABLE", 1, R.drawable.wallet_6, R.color.color_32, CategoryType.PAYABLE),
        Category(33, "RECEIVABLE", 1, R.drawable.wallet_11, R.color.color_33, CategoryType.RECEIVABLE),
        Category(34, "DEBT_INTEREST", 1, R.drawable.wallet_11, R.color.color_34, CategoryType.DEBT_INTEREST),
    )

    val listCategoryVI = listOf("Giải thưởng", "Trợ cấp", "Cổ tức", "Thưởng", "Đầu tư", "Xổ số",
        "Lương", "Tiền boa", "Khác", "Hóa đơn", "Quần áo", "Giáo dục", "Giải trí", "Thể hình",
        "Thức ăn", "Nội thất", "Quà tặng", "Sức khỏe", "Thú cưng", "Mua sắm", "Giao thông", "Du lịch",
        "Khác", "Chuyển khoản", "TRẢ NỢ", "TĂNG NỢ", "LÃI VAY", "THU NỢ",
        "TĂNG VAY", "Tiền gửi", "Rút tiền", "PHẢI TRẢ", "PHẢI THU", "LÃI NỢ")
    val listCategoryHI = listOf("पुरस्कार", "भत्ता", "लाभांश", "बोनस", "निवेश", "लॉटरी",
        "वेतन", "टिप्स", "अन्य", "बिल", "कपड़े", "शिक्षा", "मनोरंजन", "फिटनेस",
        "भोजन", "फर्नीचर", "उपहार", "स्वास्थ्य", "पालतू जानवर", "खरीदारी", "परिवहन", "यात्रा",
        "अन्य", "हस्तांतरण", "ऋण भुगतान", "ऋण वृद्धि", "ऋण ब्याज", "ऋण वसूली",
        "ऋण वृद्धि", "जमा", "निकासी", "देय", "प्राप्य", "ऋण ब्याज")
    val listCategoryZH = listOf("奖励", "津贴", "股息", "奖金", "投资", "彩票",
        "工资", "小费", "其他", "账单", "衣物", "教育", "娱乐", "健身",
        "食物", "家具", "礼物", "健康", "宠物", "购物", "交通", "旅行",
        "其他", "转账", "还款", "债务增加", "贷款利息", "债务收回",
        "贷款增加", "存款", "取款", "应付", "应收", "债务利息")

    fun getCategoryName(typelanguage : String, categoryId : Long): String {
        return when (typelanguage) {
            "vi" -> listCategoryVI[categoryId.toInt() - 1]
            "hi" -> listCategoryHI[categoryId.toInt() - 1]
            "zh" -> listCategoryZH[categoryId.toInt() - 1]
            else -> listCategory[categoryId.toInt() - 1].name
        }
    }
}