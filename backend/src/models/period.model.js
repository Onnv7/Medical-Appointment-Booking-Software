import mongoose from "mongoose";

const periodSchema = mongoose.Schema({
    start: {
        type: String,
        required: true,
        unique: true
    }
}, { timestamps: false });

export default mongoose.model("Period", periodSchema);