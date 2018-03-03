#import <Foundation/Foundation.h>

@interface com_mycompany_myapp_JsoupInterfaceIntegrationImpl : NSObject {
}

-(NSString*)getText:(NSString*)param;
-(NSString*)getElementByClass:(NSString*)param param1:(NSString*)param1 param2:(int)param2;
-(NSString*)getElementByTag:(NSString*)param param1:(NSString*)param1 param2:(int)param2;
-(NSString*)parseCode:(NSString*)param;
-(BOOL)isSupported;
@end
