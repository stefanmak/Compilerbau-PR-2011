/* Generated By:JavaCC: Do not edit this line. StefanMakTokenManager.java */
import yapl.interfaces.*;
import yapl.lib.*;
import yapl.impl.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/** Token Manager. */
public class StefanMakTokenManager implements StefanMakConstants
{

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x7fffff8000L) != 0L)
         {
            jjmatchedKind = 46;
            return 20;
         }
         if ((active0 & 0x2000L) != 0L)
            return 9;
         return -1;
      case 1:
         if ((active0 & 0x7ffbf70000L) != 0L)
         {
            jjmatchedKind = 46;
            jjmatchedPos = 1;
            return 20;
         }
         if ((active0 & 0x4088000L) != 0L)
            return 20;
         return -1;
      case 2:
         if ((active0 & 0x3db9d60000L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 46;
               jjmatchedPos = 2;
            }
            return 20;
         }
         if ((active0 & 0x4242210000L) != 0L)
            return 20;
         return -1;
      case 3:
         if ((active0 & 0x39bb810000L) != 0L)
         {
            jjmatchedKind = 46;
            jjmatchedPos = 3;
            return 20;
         }
         if ((active0 & 0x400560000L) != 0L)
            return 20;
         return -1;
      case 4:
         if ((active0 & 0x318a000000L) != 0L)
         {
            jjmatchedKind = 46;
            jjmatchedPos = 4;
            return 20;
         }
         if ((active0 & 0x831810000L) != 0L)
            return 20;
         return -1;
      case 5:
         if ((active0 & 0x3182000000L) != 0L)
         {
            jjmatchedKind = 46;
            jjmatchedPos = 5;
            return 20;
         }
         if ((active0 & 0x8000000L) != 0L)
            return 20;
         return -1;
      case 6:
         if ((active0 & 0x2082000000L) != 0L)
         {
            jjmatchedKind = 46;
            jjmatchedPos = 6;
            return 20;
         }
         if ((active0 & 0x1100000000L) != 0L)
            return 20;
         return -1;
      case 7:
         if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 46;
            jjmatchedPos = 7;
            return 20;
         }
         if ((active0 & 0x2002000000L) != 0L)
            return 20;
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 35:
         return jjStopAtPos(0, 39);
      case 40:
         return jjStopAtPos(0, 6);
      case 41:
         return jjStopAtPos(0, 7);
      case 44:
         return jjStopAtPos(0, 10);
      case 46:
         return jjStopAtPos(0, 12);
      case 58:
         jjmatchedKind = 14;
         return jjMoveStringLiteralDfa1_0(0x4000000000000L);
      case 59:
         return jjStopAtPos(0, 11);
      case 61:
         return jjStartNfaWithStates_0(0, 13, 9);
      case 65:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 66:
         return jjMoveStringLiteralDfa1_0(0x20000000L);
      case 67:
         return jjMoveStringLiteralDfa1_0(0x800000000L);
      case 68:
         return jjMoveStringLiteralDfa1_0(0x1004000000L);
      case 69:
         return jjMoveStringLiteralDfa1_0(0x42050000L);
      case 70:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 73:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 79:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 80:
         return jjMoveStringLiteralDfa1_0(0x180000000L);
      case 82:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 84:
         return jjMoveStringLiteralDfa1_0(0x420000L);
      case 87:
         return jjMoveStringLiteralDfa1_0(0x11000000L);
      case 91:
         return jjStopAtPos(0, 8);
      case 93:
         return jjStopAtPos(0, 9);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x200000000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x4000000000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x2000000000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      default :
         return jjMoveNfa_0(4, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 61:
         if ((active0 & 0x4000000000000L) != 0L)
            return jjStopAtPos(1, 50);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x7028000000L);
      case 102:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(1, 15, 20);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x1020000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x242210000L);
      case 111:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(1, 26, 20);
         return jjMoveStringLiteralDfa2_0(active0, 0xc00100000L);
      case 114:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(1, 19, 20);
         return jjMoveStringLiteralDfa2_0(active0, 0x190400000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000000L);
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000000L);
      case 100:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(2, 21, 20);
         else if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x2010000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 103:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x11100000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x580000000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x40000L);
      case 116:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(2, 33, 20);
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 119:
         if ((active0 & 0x4000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 38, 20);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 73:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 87:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000L);
      case 100:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(3, 20, 20);
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000000L);
      case 101:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(3, 18, 20);
         else if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(3, 22, 20);
         break;
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x100000000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000L);
      case 108:
         if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(3, 34, 20);
         return jjMoveStringLiteralDfa4_0(active0, 0x1001000000L);
      case 110:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(3, 17, 20);
         break;
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x800800000L);
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000000000L);
      case 101:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(4, 23, 20);
         else if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(4, 24, 20);
         else if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(4, 28, 20);
         return jjMoveStringLiteralDfa5_0(active0, 0x80000000L);
      case 102:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(4, 16, 20);
         break;
      case 104:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
      case 110:
         if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(4, 29, 20);
         break;
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x108000000L);
      case 116:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(4, 35, 20);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x100000000L);
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000L);
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000L);
      case 110:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(5, 27, 20);
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000000000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 36, 20);
         break;
      case 108:
         return jjMoveStringLiteralDfa7_0(active0, 0x2002000000L);
      case 109:
         if ((active0 & 0x100000000L) != 0L)
            return jjStartNfaWithStates_0(6, 32, 20);
         break;
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(7, 25, 20);
         break;
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x80000000L);
      case 121:
         if ((active0 & 0x2000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 37, 20);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x80000000L) != 0L)
            return jjStartNfaWithStates_0(8, 31, 20);
         break;
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 21;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 4:
                  if ((0xfc00ff7a00000000L & l) != 0L)
                  {
                     if (kind > 49)
                        kind = 49;
                  }
                  else if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 44)
                        kind = 44;
                     jjCheckNAdd(18);
                  }
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(15, 16);
                  if ((0x842000000000L & l) != 0L)
                  {
                     if (kind > 43)
                        kind = 43;
                  }
                  else if ((0x280000000000L & l) != 0L)
                  {
                     if (kind > 42)
                        kind = 42;
                  }
                  else if ((0x5000000000000000L & l) != 0L)
                  {
                     if (kind > 40)
                        kind = 40;
                  }
                  else if (curChar == 33)
                     jjCheckNAdd(9);
                  else if (curChar == 61)
                     jjCheckNAdd(9);
                  if (curChar == 62)
                     jjCheckNAdd(6);
                  else if (curChar == 60)
                     jjCheckNAdd(6);
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 0:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(1, 3);
                  break;
               case 1:
                  jjCheckNAddTwoStates(1, 3);
                  break;
               case 2:
                  if (curChar == 47 && kind > 5)
                     kind = 5;
                  break;
               case 3:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 5:
                  if ((0x5000000000000000L & l) != 0L && kind > 40)
                     kind = 40;
                  break;
               case 6:
                  if (curChar == 61 && kind > 40)
                     kind = 40;
                  break;
               case 7:
                  if (curChar == 60)
                     jjCheckNAdd(6);
                  break;
               case 8:
                  if (curChar == 62)
                     jjCheckNAdd(6);
                  break;
               case 9:
                  if (curChar == 61 && kind > 41)
                     kind = 41;
                  break;
               case 10:
                  if (curChar == 61)
                     jjCheckNAdd(9);
                  break;
               case 11:
                  if (curChar == 33)
                     jjCheckNAdd(9);
                  break;
               case 12:
                  if ((0x280000000000L & l) != 0L && kind > 42)
                     kind = 42;
                  break;
               case 13:
                  if ((0x842000000000L & l) != 0L && kind > 43)
                     kind = 43;
                  break;
               case 14:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(15, 16);
                  break;
               case 15:
                  if ((0xffffff7b00000000L & l) != 0L)
                     jjCheckNAddTwoStates(15, 16);
                  break;
               case 16:
                  if (curChar == 34 && kind > 47)
                     kind = 47;
                  break;
               case 17:
                  if ((0xfc00ff7a00000000L & l) != 0L && kind > 49)
                     kind = 49;
                  break;
               case 18:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 44)
                     kind = 44;
                  jjCheckNAdd(18);
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 46)
                     kind = 46;
                  jjstateSet[jjnewStateCnt++] = 20;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 4:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 46)
                        kind = 46;
                     jjCheckNAdd(20);
                  }
                  else if ((0x3800000078000001L & l) != 0L)
                  {
                     if (kind > 49)
                        kind = 49;
                  }
                  break;
               case 1:
                  jjAddStates(0, 1);
                  break;
               case 15:
                  if ((0x3ffffffeffffffffL & l) != 0L)
                     jjAddStates(2, 3);
                  break;
               case 17:
                  if ((0x3800000078000001L & l) != 0L && kind > 49)
                     kind = 49;
                  break;
               case 19:
               case 20:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 46)
                     kind = 46;
                  jjCheckNAdd(20);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(0, 1);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 21 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   1, 3, 15, 16, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, "\50", "\51", "\133", "\135", "\54", "\73", 
"\56", "\75", "\72", "\111\146", "\105\156\144\111\146", "\124\150\145\156", 
"\105\154\163\145", "\117\162", "\166\157\151\144", "\101\156\144", "\124\162\165\145", 
"\106\141\154\163\145", "\127\150\151\154\145", "\105\156\144\127\150\151\154\145", "\104\157", 
"\122\145\164\165\162\156", "\127\162\151\164\145", "\102\145\147\151\156", "\105\156\144", 
"\120\162\157\143\145\144\165\162\145", "\120\162\157\147\162\141\155", "\151\156\164", "\142\157\157\154", 
"\103\157\156\163\164", "\104\145\143\154\141\162\145", "\162\145\141\144\157\156\154\171", 
"\156\145\167", "\43", null, null, null, null, null, null, null, null, null, null, "\72\75", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x7ffffffffffc1L, 
};
static final long[] jjtoSkip = {
   0x3eL, 
};
static final long[] jjtoSpecial = {
   0x20L, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[21];
static private final int[] jjstateSet = new int[42];
static protected char curChar;
/** Constructor. */
public StefanMakTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}

/** Constructor. */
public StefanMakTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 21; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         matchedToken.specialToken = specialToken;
         return matchedToken;
      }
      else
      {
         if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
         {
            matchedToken = jjFillToken();
            if (specialToken == null)
               specialToken = matchedToken;
            else
            {
               matchedToken.specialToken = specialToken;
               specialToken = (specialToken.next = matchedToken);
            }
         }
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

}
